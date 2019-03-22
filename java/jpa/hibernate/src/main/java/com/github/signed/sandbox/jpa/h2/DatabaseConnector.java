package com.github.signed.sandbox.jpa.h2;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnector {

    private final String userName = "sa";
    private final String password = "sa";
    private final H2JdbcUrlBuilder jdbcUrlBuilder;
    private final Properties overridePropertiesFromPersistenceXml = new Properties();
    private EntityManagerFactory entityManagerFactory;
    private String persistenceUnitName;

    public DatabaseConnector(H2JdbcUrlBuilder jdbcUrlBuilder, String persistenceUnitName) {
        this.jdbcUrlBuilder = jdbcUrlBuilder;
        this.persistenceUnitName = persistenceUnitName;
    }

    public DatabaseConnector verboseLogging() {
        overridePropertiesFromPersistenceXml.setProperty("hibernate.show_sql", "true");
        overridePropertiesFromPersistenceXml.setProperty("hibernate.format_sql", "true");
        overridePropertiesFromPersistenceXml.setProperty("hibernate.use_sql_comments", "true");
        return this;
    }

    public DatabaseConnector create_drop(){
        overridePropertiesFromPersistenceXml.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        return this;
    }

    public void createEntityManagerFactory() {
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.url", jdbcUrlBuilder.buildUrl());
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.user", userName);
        overridePropertiesFromPersistenceXml.setProperty("javax.persistence.jdbc.password", password);
        overridePropertiesFromPersistenceXml.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName, overridePropertiesFromPersistenceXml);
    }

    public EntityManager entityManagerForLocalHsqlDatabase() {
        return entityManagerFactory.createEntityManager();
    }

    public void close() {
        entityManagerFactory.close();
    }

}
