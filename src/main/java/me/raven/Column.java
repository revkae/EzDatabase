package me.raven;

public class Column {

    public String name;
    public DataType type;
    public int length;
    public boolean isPrimary;

    public Column(String name, DataType type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.isPrimary = false;
    }

    public Column(String name, DataType type, int length, boolean isPrimary) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.isPrimary = isPrimary;
    }

    public static Column with(String name, DataType type, int length) {
        return new Column(name, type, length);
    }

    public static Column with(String name, DataType type, int length, boolean isPrimary) {
        return new Column(name, type, length, isPrimary);
    }
}
