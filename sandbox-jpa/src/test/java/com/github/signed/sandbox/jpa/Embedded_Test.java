package com.github.signed.sandbox.jpa;

import org.h2.tools.Server;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Embedded_Test {

    private final H2JdbcUrlBuilder jdbcUrlBuilder = new H2JdbcUrlBuilder().database("test").keepDataInMemoryUntilJvmShutdown();

    @Test
    public void basicSetupForInMemoryH2Database() throws Exception {
        Server server = Server.createTcpServer("-tcpPort", "9081", "-tcpAllowOthers");
        Connection connection = DriverManager.getConnection(jdbcUrlBuilder.buildUrl(), "sa", "sa");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE DEMO(ID INT PRIMARY KEY, comment VARCHAR)");
        statement.execute("INSERT INTO DEMO (id, comment) VALUES (1, 'hello') ");
        ResultSet resultSet = statement.executeQuery("SELECT * from DEMO");
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String comment = resultSet.getString(2);
            System.out.println(id +" "+comment);
        }
        server.start();
        server.shutdown();
    }


    /**
     * This sample program opens the same database once in embedded mode,
     * and once in the server mode. The embedded mode is faster, but only
     * the server mode supports remote connections.
     */

    public static void main(String[] args) throws Exception {

        // start the server, allows to access the database remotly
        Server server = Server.createTcpServer("-tcpPort", "9081");
        server.start();
        System.out.println("You can access the database remotely now, using the URL:");
        System.out.println("jdbc:h2:tcp://localhost:9081/~/test (user: sa, password: sa)");

        // now use the database in your application in embedded mode
        Class.forName("org.h2.Driver");

        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

        // some simple 'business usage'
        Statement stat = conn.createStatement();
        stat.execute("DROP TABLE TIMER IF EXISTS");
        stat.execute("CREATE TABLE TIMER(ID INT PRIMARY KEY, TIME VARCHAR)");
        System.out.println("Execute this a few times: SELECT TIME FROM TIMER");
        System.out.println("To stop this application (and the server), run: DROP TABLE TIMER");
        try {
            while (true) {
                // runs forever, except if you drop the table remotely
                stat.execute("MERGE INTO TIMER VALUES(1, NOW())");
                Thread.sleep(1000);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        conn.close();

        // stop the server
        server.stop();
    }

}
