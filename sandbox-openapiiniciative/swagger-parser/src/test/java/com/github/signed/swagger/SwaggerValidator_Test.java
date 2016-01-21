package com.github.signed.swagger;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import io.swagger.util.Json;

public class SwaggerValidator_Test {

    @Test
    public void api_definition_without_a_pass_is_not_valid() throws Exception {
        SwaggerValidator validator = new SwaggerValidator(null);
        SwaggerBuilder swaggerBuilder = SwaggerMother.emptyApiDefinition();
        String json = Json.mapper().writeValueAsString(swaggerBuilder.build());
        ValidationResult result = validator.validate(new JsonBlob(json));

        assertThat("should not pass because /paths is missing", result.failed());
    }

}