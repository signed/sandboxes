package com.github.signed.sandbox.jpa;

import org.h2.tools.Server;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Embedded_Test {

    private final H2JdbcUrlBuilder jdbcUrlBuilder = new H2JdbcUrlBuilder().database("test").keepDataInMemoryUntilJvmShutdown();

    @Test
    public void basicSetupForInMemoryH2Database() throws Exception {
        Server server = Server.createTcpServer("-tcpPort", "9081", "-tcpAllowOthers");
        server.start();
        writeWithJdbc();
        List<Demo> demos = readWithHibernate();
        assertThat(demos.get(0).getComment(), is("hello"));
        server.shutdown();
    }

    private List<Demo> readWithHibernate() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("the-demo-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Demo> query = entityManager.createQuery("select d from Demo d", Demo.class);
            List<Demo> demos = query.getResultList();
            transaction.commit();
            return demos;
        } catch (Exception ex) {
            if (null != transaction) {
                transaction.rollback();
            }
            StringWriter stringWriter = new StringWriter();
            ex.printStackTrace(new PrintWriter(stringWriter));
            Assert.fail(stringWriter.toString());
        }
        throw new UnreachableCodeException();
    }

    private void writeWithJdbc() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcUrlBuilder.buildUrl(), "sa", "sa");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE DEMO(ID INT PRIMARY KEY, comment VARCHAR)");
        statement.execute("INSERT INTO DEMO (id, comment) VALUES (1, 'hello') ");
        ResultSet resultSet = statement.executeQuery("SELECT * from DEMO");
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
