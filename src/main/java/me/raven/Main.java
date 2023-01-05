package me.raven;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Table table = new Table("test", true,
                Column.with("first", DataType.VARCHAR, 1000),
                Column.with("second", DataType.VARCHAR, 1000)
        );
        table.insert(
                DataValue.with("first", "hey"),
                DataValue.with("second", "yoo")
        );

        table.delete(
                DataValue.with("first", "hey")
        );
    }
}