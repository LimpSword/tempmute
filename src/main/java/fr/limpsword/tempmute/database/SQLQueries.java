package fr.limpsword.tempmute.database;

import com.google.common.collect.Maps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public final class SQLQueries {

    private SQLConnection sqlConnection;

    public SQLQueries(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Long> load() {
        Map<String, Long> map = Maps.newHashMap();
        try {
            PreparedStatement createStatement = sqlConnection.getConnection()
                    .prepareStatement("CREATE TABLE IF NOT EXISTS " + sqlConnection.getDatabase()
                            + " (id VARCHAR(36), time BIGINT)");
            createStatement.execute();
            close(null, createStatement, sqlConnection.getConnection());


            PreparedStatement preparedStatement = sqlConnection.getConnection()
                    .prepareStatement("SELECT id, time FROM " + sqlConnection.getDatabase());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                map.put(resultSet.getString("id"), resultSet.getLong("time"));
            }
            close(resultSet, preparedStatement, sqlConnection.getConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }

    public void save(Map<String, Long> map) {
        try {
            PreparedStatement dropStatement = sqlConnection.getConnection()
                    .prepareStatement("TRUNCATE TABLE " + sqlConnection.getDatabase());
            dropStatement.execute();
            close(null, dropStatement, sqlConnection.getConnection());

            if (map.size() == 0) return;

            StringBuilder sql = new StringBuilder();
            for (int i = 0; i < map.size(); i++) {
                sql.append("INSERT INTO ").append(sqlConnection.getDatabase()).append(" VALUES (?, ?)");
            }
            PreparedStatement preparedStatement = sqlConnection.getConnection()
                    .prepareStatement(sql.toString());

            int id = 1;
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                preparedStatement.setString(id++, entry.getKey());
                preparedStatement.setLong(id++, entry.getValue());
            }

            preparedStatement.executeUpdate();
            close(null, dropStatement, sqlConnection.getConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}