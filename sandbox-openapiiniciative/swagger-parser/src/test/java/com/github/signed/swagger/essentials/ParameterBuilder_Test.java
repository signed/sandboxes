package com.github.signed.swagger.essentials;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.swagger.models.parameters.HeaderParameter;

public class ParameterBuilder_Test {

    @Test
    public void produce_header_parameter() throws Exception {
        ParameterBuilder builder = new ParameterBuilder()
                .withName("Authorization")
                .ofTypeString().inHeader().withDescription("Awsome").required();
        assertThat(builder.build(), Matchers.instanceOf(HeaderParameter.class));
    }
}