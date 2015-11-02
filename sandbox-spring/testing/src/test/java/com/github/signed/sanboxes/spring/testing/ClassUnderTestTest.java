package com.github.signed.sanboxes.spring.testing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    }

    @Autowired
    ClassUnderTest classUnderTest;

    @Test
    public void name() throws Exception {
        assertThat(classUnderTest.fetchInt(), is(7));
    }
}