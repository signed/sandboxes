package com.github.signed.sandbox.jpa.customtype;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.signed.sandbox.jpa.h2.DatabaseConnector;
import com.github.signed.sandbox.jpa.h2.DatabaseServer;
import com.github.signed.sandbox.jpa.h2.H2JdbcUrlBuilder;
import com.github.signed.sandbox.jpa.h2.JpaDatabase;

public class CustomTypeMappingTest {
    private final H2JdbcUrlBuilder jdbcUrlBuilder = new H2JdbcUrlBuilder().database("test").keepDataInMemoryUntilJvmShutdown();
    private final DatabaseConnector connector = new DatabaseConnector(jdbcUrlBuilder, "custom-types");
    private final JpaDatabase jpaDatabase = new JpaDatabase(connector);
    private final DatabaseServer server = new DatabaseServer(9081);

    @Before
    public void setUp() throws Exception {
        connector.createEntityManagerFactory();
        server.start();
    }

    @After
    public void stopServer() {
        connector.close();
        server.stop();
    }

    @Test
    public void eins() throws Exception {
        Person person = new Person(23);
        jpaDatabase.persist(person);

        assertThat(person.id, Matchers.notNullValue());
    }

}
