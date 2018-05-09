package com.github.signed.sandboxes.spring.data.customers;

import com.github.signed.sandboxes.spring.data.CustomerRepository;
import com.github.signed.sandboxes.spring.data.H2Configuration;
import com.github.signed.sandboxes.spring.data.HibernateConfiguration;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {CustomerRepositoryTest.SchemaInitializer.class}, mergeMode = MERGE_WITH_DEFAULTS)
@SpringBootTest(classes = {H2Configuration.class, HibernateConfiguration.class})
public class CustomerRepositoryTest {

    public static class SchemaInitializer extends AbstractTestExecutionListener {
        @Override
        public void beforeTestClass(TestContext testContext) {
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

    @Autowired
    private CustomerRepository repository;

    @Test
    public void saveAndRead() {
        Customer john = new Customer("John", "Doe");
        Customer save = repository.save(john);
        assertThat(save.id, Matchers.notNullValue());

        List<Customer> doe = repository.findByLastName("Doe");

        assertThat(doe.get(0).firstName, is("John"));
    }
}
