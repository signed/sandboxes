package com.github.signed.swagger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import io.swagger.models.Swagger;

public class SwaggerTrim_Test {
    private final SwaggerBuilder swaggerBuilder = SwaggerMother.emptyApiDefinition();

    @Test
    public void trim_of_empty_swagger_definition_should_work() throws Exception {
        assertThat(trimmed().getTags(), nullValue());
    }

    @Test
    public void trim_a_swagger_with_untagged_path_definition() throws Exception {
        swaggerBuilder.withPath("/").withPost();

        assertThat(trimmed(), not(nullValue()));
    }

    private Swagger trimmed() {
        return new SwaggerTrim().trim(swaggerBuilder.build());
    }
}