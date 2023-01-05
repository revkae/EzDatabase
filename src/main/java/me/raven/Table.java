package me.raven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class Table {

    private String tableName;
    private List<Column> columns;

    public Table(String tableName, boolean exists, Column... columns) {
        this.tableName = tableName;
        this.columns = List.of(columns);

        try {
            StringJoiner statementArgs = new StringJoiner(",");
            for (Column column : columns) {
                statementArgs.add(column.name + " " + column.type + " (" + column.length + ")");

                if (column.isPrimary) {
                    statementArgs.add("PRIMARY KEY(" + column.name + ")");
                }
            }
//            connection.prepareStatement(
//                    "CREATE TABLE IF NOT EXISTS "
//                            + tableName +
//                            " (id int NOT NULL AUTO_INCREMENT, first varchar(255), last varchar(255), PRIMARY KEY(id))"
            Connection connection = Database.get().getConnection();

            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS "
                            + tableName
                            + "(" + statementArgs + ")"
                    );
            if (!exists) {
                statement = connection.prepareStatement(
                        "CREATE TABLE "
                                + tableName
                                + "(" + statementArgs + ")"
                );
            }

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String... data) {
        try {
            Connection connection = Database.get().getConnection();

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName + " "
            )
            String sql = "INSERT INTO customers (first_name, last_name) VALUES (?, ?)";
            Prepared Statement statement = connection.prepare Statement(sql);
            statement.setString(1, "John");
            statement.setString(2, "Smith");
            int rowsAffected = statement.execute Update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
