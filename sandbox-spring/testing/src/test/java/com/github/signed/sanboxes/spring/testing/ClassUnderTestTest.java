package com.github.signed.sanboxes.spring.testing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ClassUnderTestTest {

    @Configuration
    @ComponentScan(basePackageClasses = {ClassUnderTest.class}, resourcePattern = "ClassUnderTest.class")
    static class Config {

        @Bean
        public static StubsForEveryone stubsForEveryone(){
            return new StubsForEveryone();
        }

    }

    public static class StubsForEveryone implements BeanFactoryPostProcessor{

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            for (String s : beanFactory.getBeanDefinitionNames()) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(s);
                System.out.println(s);
            }

            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

            GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
            genericBeanDefinition.setBeanClass(DependencyNotInApplicationContext.class);
            //registry.registerBeanDefinition("goaway", genericBeanDefinition);

        }
    }

    @Autowired
    ClassUnderTest classUnderTest;

    @Test
    public void name() throws Exception {
        assertThat(classUnderTest.fetchInt(), is(7));
    }
}