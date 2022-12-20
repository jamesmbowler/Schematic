package com.bjoernkw.schematic.dbtypes;

public class PostgresQueries implements DbQueries {

    @Override
    public String getTables() {
        return "SELECT table_name FROM INFORMATION_SCHEMA.Tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'";
    }
}