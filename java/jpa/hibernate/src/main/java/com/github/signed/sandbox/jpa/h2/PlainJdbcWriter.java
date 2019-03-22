package com.github.signed.sandbox.jpa.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlainJdbcWriter {
    private H2JdbcUrlBuilder jdbcUrlBuilder;

    public PlainJdbcWriter(H2JdbcUrlBuilder jdbcUrlBuilder){
        this.jdbcUrlBuilder = jdbcUrlBuilder;
    }

    public void writeIntoTable(String tableName) throws SQLException {
        String jdbcUrl = jdbcUrlBuilder.buildUrl();
        System.out.println("jdbcUrl = " + jdbcUrl);
        Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "sa");
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO " + tableName + " (id, comment) VALUES (1, 'hello " + tableName + "') ");
        ResultSet resultSet = statement.executeQuery("SELECT * from " + tableName);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String comment = resultSet.getString(2);
            System.out.println(id + " " + comment);
        }
        statement.close();
        connection.commit();
        connection.close();
    }
}
