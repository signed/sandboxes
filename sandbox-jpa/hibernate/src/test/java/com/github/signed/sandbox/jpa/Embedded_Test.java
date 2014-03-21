package com.github.signed.sandbox.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.h2.tools.Server;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.github.signed.sandbox.jpa.h2.DatabaseConnector;
import com.github.signed.sandbox.jpa.h2.H2JdbcUrlBuilder;
import com.github.signed.sandbox.jpa.h2.JpaDatabase;

public class Embedded_Test {

    private final H2JdbcUrlBuilder jdbcUrlBuilder = new H2JdbcUrlBuilder().database("test").keepDataInMemoryUntilJvmShutdown();
    private final DatabaseConnector connector = new DatabaseConnector(jdbcUrlBuilder);

    @Before
    public void setUp() throws Exception {
        connector.createEntityManagerFactory();
    }

    @Test
    public void basicSetupForInMemoryH2Database() throws Exception {
        Server server = Server.createTcpServer("-tcpPort", "9081", "-tcpAllowOthers");
        server.start();
        Demo demo = new Demo();
        demo.setComment("hello DEMO");
        new JpaDatabase(connector).persist(demo);
        List<Demo> demos = readWithHibernate();
        assertThat(demos.get(0).getComment(), is("hello DEMO"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadHibernateMappingsFromAnotherJar() throws Exception {
        Server server = Server.createTcpServer("-tcpPort", "9082", "-tcpAllowOthers");
        server.start();
        World world = new World();
        world.setComment("hello WORLD");
        new JpaDatabase(connector).persist(world);
    }

    private List<Demo> readWithHibernate() {
        EntityManager entityManager = connector.entityManagerForLocalHsqlDatabase();
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


}
