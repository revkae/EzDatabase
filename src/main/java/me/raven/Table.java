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
            System.out.println(statementArgs);
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

    public void insert(DataValue... dataValue) {
        try {
            Connection connection = Database.get().getConnection();

            StringJoiner names = new StringJoiner(",");
            StringJoiner values = new StringJoiner(",");

            for (DataValue value : dataValue) {
                names.add(value.name);
                values.add("?");
            }

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO "
                    + tableName
                    + "(" + names + ")"
                    + "VALUES"
                    + "(" + values + ")"
            );

            int num = 1;
            for (DataValue value : dataValue) {
                if (value.value instanceof String)
                    statement.setString(num, (String) value.value);
                else if (value.value instanceof Integer)
                    statement.setInt(num, (Integer) value.value);
                else if (value.value instanceof Double)
                    statement.setDouble(num, (Double) value.value);
                else if (value.value instanceof Boolean)
                    statement.setBoolean(num, (Boolean) value.value);
                num++;
            }

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(DataValue dataValue) {
        try {
            Connection connection = Database.get().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM "
                    + tableName
                    + " WHERE "
                    + dataValue.name
                    + " = "
                    + "'" + dataValue.value + "'"
            );

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
