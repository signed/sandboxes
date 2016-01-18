package com.github.signed.swagger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.RefProperty;

public class Properties_Test {

    private final Properties properties = new Properties();

    @Test
    public void for_ref_property_just_return_the_DefinitionReference() throws Exception {
        RefProperty refProperty = new RefProperty("some-model");

        assertThat(properties.definitionReferencesIn(refProperty).get(0).getSimpleRef(), is("some-model"));
    }

    @Test
    public void for_array_property_the_element_type_needs_to_be_checked() throws Exception {
        ArrayProperty arrayProperty = new ArrayProperty();
        arrayProperty.setItems(new RefProperty("element-type"));

        assertThat(properties.definitionReferencesIn(arrayProperty).get(0).getSimpleRef(), is("element-type")) ;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void for_map_property_the_value_elements_need_to_be_checked() throws Exception {
        MapProperty arrayProperty = new MapProperty();

        assertThat(properties.definitionReferencesIn(arrayProperty), not(empty())) ;
    }

}