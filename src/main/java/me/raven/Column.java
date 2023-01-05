package me.raven;

public class Column {

    public String name;
    public DataType type;
    public int length;
    public boolean isPrimary;

    public Column(String name, DataType type, int length) {
        this.name = name;
        this.type = type;
        this.isPrimary = false;
    }

    public Column(String name, DataType type, int length, boolean isPrimary) {
        this.name = name;
        this.type = type;
        this.isPrimary = isPrimary;
    }
}
