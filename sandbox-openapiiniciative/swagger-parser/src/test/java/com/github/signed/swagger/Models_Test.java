package com.github.signed.swagger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.swagger.models.ArrayModel;
import io.swagger.models.properties.StringProperty;

public class Models_Test {

    private final Models models = new Models();
    private final ArrayModel arrayModel = new ArrayModel();

    @Test
    public void extract_definition_references_from_items_property() throws Exception {
        arrayModel.setItems(new RefPropertyBuilder().withReferenceTo("referenced-model").build());

        assertThat(models.definitionReferencesIn(arrayModel).get(0).getSimpleRef(), is("referenced-model"));
    }

    @Test
    public void leave_none_ref_properties_alone() throws Exception {
        arrayModel.setItems(new StringProperty());

        assertThat(models.definitionReferencesIn(arrayModel), Matchers.empty());
    }
}