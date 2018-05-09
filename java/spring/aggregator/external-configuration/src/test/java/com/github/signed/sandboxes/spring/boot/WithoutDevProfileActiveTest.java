package com.github.signed.sandboxes.spring.boot;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ExternalConfigurationBootApplication.class})
@ActiveProfiles("dev")
public class WithoutDevProfileActiveTest implements EnvironmentFixture{

    @Autowired
    private Environment environment;

    @Test
    public void returnPropertyFromApplicationProperties() throws Exception {
        assertThat(property("application.global"), is("overlord"));
    }

    @Test
    public void propertyDefinedInTheDevPropertiesIsPresent() throws Exception {
        assertThat(property("only.present.in.dev"), is("wtf"));
    }

    @Test
    public void propertyValueOverriddenInDevPropertiesIsPresent() throws Exception {
        assertThat(property("overridden.in.dev"), is("itwonthurt"));
    }

    @Override
    public Environment environment() {
        return environment;
    }
}