package com.github.signed.sandbox.jpa.h2;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class JpaDatabase {

    private DatabaseConnector connector;

    public JpaDatabase(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void persist(Object entity) {
        EntityManager entityManager = connector.entityManagerForLocalHsqlDatabase();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Throwable throwable) {
            transaction.rollback();
            throw throwable;
        } finally {
            entityManager.close();
        }
    }
}
