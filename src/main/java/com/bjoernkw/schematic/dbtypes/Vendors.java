package com.bjoernkw.schematic.dbtypes;

public enum Vendors {
    MYSQL("MySQL"),
    POSTGRES("PostgreSQL");

    public final String name;
    Vendors(String name) {
        this.name = name;
    }

    public static Vendors fromString(String text) {
        for (Vendors v : Vendors.values()) {
            if (v.name.equals(text)) {
                return v;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}