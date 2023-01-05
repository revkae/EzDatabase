package me.raven;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Table table = new Table("test", true,
                new Column("first", DataType.VARCHAR, 100),
                new Column("second", DataType.VARCHAR, 100)
        );
    }
}