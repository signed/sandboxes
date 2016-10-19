package com.github.signed.sandbox.spring.ioc.proxies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.ResourceTransactionManager;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ProxiesForBeansFromProducerMethod {

    @Configuration
    @EnableTransactionManagement
    public static class Config {

        @Bean
        public Protocol protocol() {
            return new GhostProtocol();
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(){
            return new ResourceTransactionManager() {
                @Override
                public Object getResourceFactory() {
                    return null;
                }

                @Override
                public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {
                    return null;
                }

                @Override
                public void commit(TransactionStatus transactionStatus) throws TransactionException {

                }

                @Override
                public void rollback(TransactionStatus transactionStatus) throws TransactionException {

                }
            };
        }
    }

    public interface Protocol {
        void invoke();
    }

    public static class GhostProtocol implements Protocol {

        @Override
        @Transactional
        public void invoke() {
            System.out.println("ghost activated");
        }
    }

    @Autowired
    private Protocol protocol;

    @Test
    public void springCanProxyObjectsEvenIfTheyWhereNotInstantiatedBySpring() throws Exception {
        protocol.invoke();
        assertThat("expected the transaction proxy", AopUtils.isJdkDynamicProxy(protocol));
    }
}
