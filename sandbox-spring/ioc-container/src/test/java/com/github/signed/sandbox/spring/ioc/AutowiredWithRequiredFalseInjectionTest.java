package com.github.signed.sandbox.spring.ioc;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AutowiredWithRequiredFalseInjectionTest {

    @Configuration
    @ComponentScan(basePackageClasses = {BeanWithOptionalDependency.class}, resourcePattern = "BeanWithOptionalDependency*.class" )
    static class Config {
        @Bean
        RequiredDependency requiredDependency() {
            return new RequiredDependency();
        }
    }

    @Autowired
    BeanWithOptionalDependency bean;
    @Autowired
    BeanWithOptionalDependencyJava8 beanJava8;

    @Test
    public void beanCanBeInstantiated() throws Exception {
        assertThat(bean, notNullValue());
        assertThat(beanJava8, notNullValue());
    }

    @Test
    public void optionalBeanIsAbsent() throws Exception {
        assertThat("optional bean should not be present with this configuration", ! bean.optionalBeanPresent());
        assertThat("optional bean should not be present with this configuration", ! beanJava8.optionalBeanPresent());
    }
}