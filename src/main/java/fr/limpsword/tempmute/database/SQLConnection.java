package fr.limpsword.tempmute.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class SQLConnection {

    private final Logger logger = Bukkit.getLogger();

    private String database;
    private BasicDataSource dataSource;

    public void enable(FileConfiguration configuration, Runnable runnable) {
        logger.info("[Tempmute] Starting connection to database...");

        String host = configuration.getString("host");
        int port = configuration.getInt("port");
        database = configuration.getString("database");
        String username = configuration.getString("username");
        String password = configuration.getString("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");

            this.dataSource = new BasicDataSource();
            this.dataSource.setUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?allowMultiQueries=true&createDatabaseIfNotExist=true");
            this.dataSource.setUsername(username);
            this.dataSource.setPassword(password);

            runnable.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disable() {
        logger.info("[Tempmute] Ending connection to database...");

        try {
            dataSource.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getDatabase() {
        return database;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}