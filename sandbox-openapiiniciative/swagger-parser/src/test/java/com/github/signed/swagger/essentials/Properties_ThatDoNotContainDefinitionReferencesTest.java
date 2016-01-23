package com.github.signed.swagger.essentials;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import io.swagger.models.properties.DateProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.EmailProperty;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.PasswordProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;

@RunWith(Parameterized.class)
public class Properties_ThatDoNotContainDefinitionReferencesTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new PasswordProperty()}
                , {new StringProperty()}
                , {new EmailProperty()}
                , {new DateTimeProperty()}
                , {new IntegerProperty()}
                , {new DateProperty()}
                , {new DateProperty()}

        });
    }

    private final Properties properties = new Properties();
    private final Property property;


    public Properties_ThatDoNotContainDefinitionReferencesTest(Property property) {
        this.property = property;
    }

    @Test
    public void test() {
        assertThat(properties.definitionReferencesIn(property), Matchers.empty());
    }

}