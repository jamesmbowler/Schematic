package com.bjoernkw.schematic.dbtypes;

public class MysqlQueries implements DbQueries {

    String dbName;
    public MysqlQueries(String dbName) {
        this.dbName = dbName;
    }
    @Override
    public String  getTables() {
        return "SELECT table_name FROM INFORMATION_SCHEMA.Tables WHERE table_schema = '"+this.dbName+"'";
    }
}