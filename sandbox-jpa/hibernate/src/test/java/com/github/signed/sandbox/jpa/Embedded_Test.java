package com.github.signed.sandbox.jpa;

import org.h2.tools.Server;
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
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Embedded_Test {

    private final H2JdbcUrlBuilder jdbcUrlBuilder = new H2JdbcUrlBuilder().database("test").keepDataInMemoryUntilJvmShutdown();
    private final String userName = "sa";
    private final String password = "sa";

    @Test
    public void basicSetupForInMemoryH2Database() throws Exception {
        Server server = Server.createTcpServer("-tcpPort", "9081", "-tcpAllowOthers");
        server.start();
        writeIntoTable("DEMO");
        List<Demo> demos = readWithHibernate();
        assertThat(demos.get(0).getComment(), is("hello DEMO"));
        server.shutdown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadHibernateMappingsFromAnotherJar() throws Exception {
        Server server = Server.createTcpServer("-tcpPort", "9082", "-tcpAllowOthers");
        server.start();
        writeIntoTable("WORLD");
        List<World> worlds = readWithHibernateMappingsInAnotherArtifact();
        assertThat(worlds.get(0).getComment(), is( "hello WORLD"));
        server.shutdown();
    }

    private List<World> readWithHibernateMappingsInAnotherArtifact() {
        Properties overridePropertiesFromPersistenceXml = new Properties();
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.url", jdbcUrlBuilder.buildUrl());
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.user", userName);
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.password", password);
        overridePropertiesFromPersistenceXml.setProperty("hibernate.show_sql", "false");
        overridePropertiesFromPersistenceXml.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("the-demo-unit", overridePropertiesFromPersistenceXml);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<World> query = entityManager.createQuery("select w from World w", World.class);
            List<World> worlds = query.getResultList();
            transaction.commit();
            return worlds;
        } catch (RuntimeException ex) {
            if (null != transaction) {
                transaction.rollback();
            }
            StringWriter stringWriter = new StringWriter();
            ex.printStackTrace(new PrintWriter(stringWriter));
            throw ex;
        }
    }

    private List<Demo> readWithHibernate() {
        Properties overridePropertiesFromPersistenceXml = new Properties();
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.url", jdbcUrlBuilder.buildUrl());
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.user", userName);
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.password", password);
        overridePropertiesFromPersistenceXml.setProperty("hibernate.show_sql", "false");
        overridePropertiesFromPersistenceXml.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("the-demo-unit", overridePropertiesFromPersistenceXml);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Demo> query = entityManager.createNamedQuery("Demo.findAll", Demo.class);
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

    private void writeIntoTable(String tableName) throws SQLException {
        String jdbcUrl = jdbcUrlBuilder.buildUrl();
        System.out.println("jdbcUrl = " + jdbcUrl);
        Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "sa");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE "+tableName+"(ID INT PRIMARY KEY, comment VARCHAR)");
        statement.execute("INSERT INTO "+tableName+" (id, comment) VALUES (1, 'hello "+tableName+"') ");
        ResultSet resultSet = statement.executeQuery("SELECT * from "+tableName);
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
