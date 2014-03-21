package com.github.signed.sandbox.jpa;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnector {

    private final String userName = "sa";
    private final String password = "sa";
    private final H2JdbcUrlBuilder jdbcUrlBuilder;
    private EntityManagerFactory entityManagerFactory;

    public DatabaseConnector(H2JdbcUrlBuilder jdbcUrlBuilder) {
        this.jdbcUrlBuilder = jdbcUrlBuilder;
    }

    public void createEntityManagerFactory(){
        Properties overridePropertiesFromPersistenceXml = new Properties();
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.url", jdbcUrlBuilder.buildUrl());
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.user", userName);
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.password", password);
        overridePropertiesFromPersistenceXml.setProperty("hibernate.show_sql", "false");
        overridePropertiesFromPersistenceXml.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        entityManagerFactory = Persistence.createEntityManagerFactory("the-demo-unit", overridePropertiesFromPersistenceXml);
    }

    public EntityManager entityManagerForLocalHsqlDatabase() {
        createEntityManagerFactory();
        return entityManagerFactory.createEntityManager();
    }

    public void close(){
        entityManagerFactory.close();
    }

}
