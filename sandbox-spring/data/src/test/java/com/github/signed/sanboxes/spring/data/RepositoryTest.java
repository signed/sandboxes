package com.github.signed.sanboxes.spring.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RepositoryTest {

    @Configuration
    @EnableJpaRepositories
    @EnableTransactionManagement
    public static class Config {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
            LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
            lef.setDataSource(dataSource);
            lef.setJpaVendorAdapter(jpaVendorAdapter);
            lef.setPackagesToScan(Customer.class.getPackage().getName());
            return lef;
        }

        @Bean
        public JpaVendorAdapter jpaVendorAdapter() {
            HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
            hibernateJpaVendorAdapter.setShowSql(false);
            hibernateJpaVendorAdapter.setGenerateDdl(true);
            hibernateJpaVendorAdapter.setDatabase(Database.H2);
            return hibernateJpaVendorAdapter;
        }

        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }

    }

    @Autowired
    private CustomerRepository repository;

    @Test
    public void saveAndRead() throws Exception {
        Customer john = new Customer("John", "Doe");
        Customer save = repository.save(john);
        assertThat(save.id, Matchers.notNullValue());

        List<Customer> doe = repository.findByLastName("Doe");

        assertThat(doe.get(0).firstName, is("John"));
    }
}
