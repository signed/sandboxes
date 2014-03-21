package com.github.signed.sandbox.jpa.lazyloading;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.signed.sandbox.jpa.h2.DatabaseConnector;
import com.github.signed.sandbox.jpa.h2.DatabaseServer;
import com.github.signed.sandbox.jpa.h2.H2JdbcUrlBuilder;
import com.github.signed.sandbox.jpa.h2.JpaDatabase;

public class OverrideLazyLoading_Test {
    private final H2JdbcUrlBuilder jdbcUrlBuilder = new H2JdbcUrlBuilder().database("test").keepDataInMemoryUntilJvmShutdown();
    private final DatabaseConnector connector = new DatabaseConnector(jdbcUrlBuilder);
    private final JpaDatabase jpaDatabase = new JpaDatabase(connector);
    private final DatabaseServer server = new DatabaseServer(9081);

    @Before
    public void setUp() throws Exception {
        connector.createEntityManagerFactory();
        server.start();
    }

    @After
    public void stopServer(){
        connector.close();
        server.stop();
    }

    @Test
    public void testName() throws Exception {


    }
}
