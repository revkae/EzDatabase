package me.raven;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Table {

    private String tableName;
    private List<Column> columns;

    public Table(String tableName, Column... columns) {
        this.tableName = tableName;
        this.columns = List.of(columns);

        StringJoiner statementArgs = new StringJoiner(",");
        for (Column column : columns) {
            statementArgs.add(column.name + " " + column.type + " (" + column.length + ")");

            if (column.isPrimary) {
                statementArgs.add("PRIMARY KEY(" + column.name + ")");
            }
        }

        try (PreparedStatement statement = Database.get().getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS "
                        + tableName
                        + "(" + statementArgs + ")")) {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(List<DataValue> whereValues, DataValue... dataValues) {
        StringJoiner sets = new StringJoiner(", ");
        for (DataValue dataValue : dataValues) {
            sets.add(dataValue.name + " = '" + dataValue.value + "'");
        }

        StringJoiner wheres = new StringJoiner(",");
        for (DataValue whereValue : whereValues) {
            wheres.add(whereValue.name + " = '" + whereValue.value + "'");
        }

        try (PreparedStatement statement = Database.get().getConnection().prepareStatement(
                "UPDATE "
                        + tableName
                        + " SET "
                        + sets
                        + " WHERE "
                        + wheres)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(DataValue... dataValue) {
        StringJoiner names = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");

        for (DataValue value : dataValue) {
            names.add(value.name);
            values.add("?");
        }

        try (PreparedStatement statement = Database.get().getConnection().prepareStatement(
                "INSERT INTO "
                        + tableName
                        + "(" + names + ")"
                        + "VALUES"
                        + "(" + values + ")")) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Row row) {
        StringJoiner names = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");

        for (DataValue value : row.getRow()) {
            names.add(value.name);
            values.add("?");
        }

        try (PreparedStatement statement = Database.get().getConnection().prepareStatement(
                "INSERT INTO "
                        + tableName
                        + "(" + names + ")"
                        + "VALUES"
                        + "(" + values + ")")) {

            int num = 1;
            for (DataValue value : row.getRow()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(DataValue... dataValues) {
        StringJoiner whereValues = new StringJoiner(" AND ");
        for (DataValue dataValue : dataValues) {
            whereValues.add(dataValue.name + " = '" + dataValue.value + "'");
        }

        try (PreparedStatement preparedStatement = Database.get().getConnection().prepareStatement(
                "DELETE FROM "
                        + tableName
                        + " WHERE "
                        + whereValues)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Row row) {
        StringJoiner whereValues = new StringJoiner(" AND ");
        for (DataValue dataValue : row.getRow()) {
            whereValues.add(dataValue.name + " = '" + dataValue.value + "'");
        }

        try (PreparedStatement preparedStatement = Database.get().getConnection().prepareStatement(
                "DELETE FROM "
                        + tableName
                        + " WHERE "
                        + whereValues)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void get(String name) {
        try (PreparedStatement statement = Database.get().getConnection().prepareStatement(
                "SELECT " +
                        name +
                        " FROM " +
                        tableName
        ); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DataValue> getColumn(String name) {
        List<DataValue> output = new ArrayList<>();

        try (PreparedStatement statement = Database.get().getConnection().prepareStatement(
                "SELECT " +
                        name +
                        " FROM " +
                        tableName
        ); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                output.add(DataValue.with(name, resultSet.getString(name)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
}
