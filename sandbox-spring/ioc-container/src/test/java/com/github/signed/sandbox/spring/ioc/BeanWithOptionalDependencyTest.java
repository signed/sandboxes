package com.github.signed.sandbox.spring.ioc;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BeanWithOptionalDependencyTest {

    @Configuration
    static class Config {

        @Bean
        BeanWithOptionalDependency beanWithOptionalDependency() {
            return new BeanWithOptionalDependency();
        }

        @Bean
        RequiredDependency requiredDependency() {
            return new RequiredDependency();
        }

    }

    @Autowired
    BeanWithOptionalDependency bean;

    @Test
    public void beanCanBeInstantiated() throws Exception {
        assertThat(bean, notNullValue());
    }

    @Test
    public void optionalBeanIsAbsent() throws Exception {
        assertThat("optional bean should not be present with this configuration", bean.optionalBeanPresent());
    }
}