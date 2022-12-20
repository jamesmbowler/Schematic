package com.bjoernkw.schematic;

import com.bjoernkw.schematic.dbtypes.DbQueries;
import com.bjoernkw.schematic.dbtypes.MysqlQueries;
import com.bjoernkw.schematic.dbtypes.PostgresQueries;
import com.bjoernkw.schematic.dbtypes.Vendors;
import io.github.wimdeblauwe.hsbt.mvc.HxRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/schematic/tables")
public class TablesController {

    private static final String VIEW_MODEL_NAME = "tables";

    private final JdbcTemplate jdbcTemplate;
    private final DbQueries dbQueries;

    public TablesController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        try {
            Vendors vendor = Vendors.fromString(jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName());
            this.dbQueries = switch (vendor) {
                case MYSQL -> new MysqlQueries(jdbcTemplate.getDataSource().getConnection().getCatalog());
                case POSTGRES -> new PostgresQueries();
                default -> throw new Exception("No vendor " + vendor.name);
            };

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public String listTables(Model model) {
        model.addAttribute(
                VIEW_MODEL_NAME,
                getTables()
        );

        return "index";
    }

    @DeleteMapping("/{tableName}")
    @HxRequest
    public String dropTable(@PathVariable String tableName, Model model) {
        List<Table> availableTables = getTables();
        if (availableTables.stream().anyMatch(table -> table.getTableName().equals(tableName))) {
            jdbcTemplate.execute("DROP TABLE " + tableName);
        }

        model.addAttribute(
                VIEW_MODEL_NAME,
                getTables()
        );

        return "fragments/tables";
    }

    @DeleteMapping("/{tableName}/truncate")
    @HxRequest
    public String truncateTable(@PathVariable String tableName, Model model) {
        List<Table> availableTables = getTables();
        if (availableTables.stream().anyMatch(table -> table.getTableName().equals(tableName))) {
            jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
        }

        model.addAttribute(
                VIEW_MODEL_NAME,
                getTables()
        );

        return "fragments/tables";
    }

    private List<Table> getTables() {
        List<Table> tables = jdbcTemplate.query(
                this.dbQueries.getTables(),
                new BeanPropertyRowMapper<>(Table.class)
        );
        tables.forEach(table -> {
            table.setColumns(
                    jdbcTemplate.query(
                            "SELECT column_name, data_type FROM INFORMATION_SCHEMA.Columns WHERE table_name = ?",
                            new BeanPropertyRowMapper<>(Column.class),
                            table.getTableName()
                    )
            );
            table.setEntries(jdbcTemplate.queryForList("SELECT * FROM " + table.getTableName()));
        });

        return tables;
    }
}
