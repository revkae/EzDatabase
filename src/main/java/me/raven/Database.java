package me.raven;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private static Database instance = null;
    private HikariDataSource hikariDataSource;

    public Database() {
        instance = this;
        init();
    }

    private void init() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("verifyServerCertificate", "true");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setAutoCommit(true);
        hikariConfig.setMaximumPoolSize(15);

        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() {
        try {
            if (hikariDataSource == null) {
                return null;
            }
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Database get() {
        return instance;
    }
}
