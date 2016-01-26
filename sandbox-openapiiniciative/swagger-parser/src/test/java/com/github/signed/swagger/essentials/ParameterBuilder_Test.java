package com.github.signed.swagger.essentials;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;

public class ParameterBuilder_Test {

    @Test
    public void produce_header_parameter() throws Exception {
        ParameterBuilder builder = new ParameterBuilder()
                .withName("Authorization")
                .ofTypeString().inHeader().withDescription("Awsome").required();
        assertThat(builder.build(), Matchers.instanceOf(HeaderParameter.class));
    }

    @Test
    public void refparameter_take_their_name_from_the_parameter_definition_that_is_referenced() throws Exception {
        Parameter build = new ParameterBuilder().referencingParameterDefinition("some-definition").withName("should be ignored by the builder").build();

        assertThat(build.getName(), nullValue());
    }
}