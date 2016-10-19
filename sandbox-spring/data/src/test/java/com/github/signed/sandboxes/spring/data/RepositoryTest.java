package com.github.signed.sandboxes.spring.data;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {RepositoryTest.SchemaInitializer.class}, mergeMode = MERGE_WITH_DEFAULTS)
@SpringApplicationConfiguration
public class RepositoryTest {

    public static class SchemaInitializer extends AbstractTestExecutionListener {
        @Override
        public void beforeTestClass(TestContext testContext) throws Exception {
            DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update("CREATE TABLE Customer(\n" +
                    "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                    "  `firstName` varchar(32),\n" +
                    "  `lastName` varchar(32),\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ");
        }
    }

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
            hibernateJpaVendorAdapter.setShowSql(true);
            hibernateJpaVendorAdapter.setGenerateDdl(false);
            hibernateJpaVendorAdapter.setDatabase(Database.H2);
            Map<String, Object> propertyMap = hibernateJpaVendorAdapter.getJpaPropertyMap();
            propertyMap.put("hibernate.show_sql", "true");
            propertyMap.put("hibernate.format_sql", "true");
            propertyMap.put("hibernate.use_sql_comments", "true");

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
