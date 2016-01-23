package com.github.signed.swagger.validate;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.function.Supplier;

import org.junit.Test;

import com.github.signed.swagger.essentials.SwaggerBuilder;
import com.github.signed.swagger.essentials.SwaggerMother;

import io.swagger.util.Json;

public class SwaggerValidate_Test {

    @Test
    public void api_definition_without_a_path_is_not_valid() throws Exception {
        Supplier<String> schemaSupplier = new SwaggerSchemaSupplier();
        SwaggerValidate validator = new SwaggerValidate(schemaSupplier);
        SwaggerBuilder swaggerBuilder = SwaggerMother.emptyApiDefinition();
        String json = Json.mapper().writeValueAsString(swaggerBuilder.build());
        ValidationResult result = validator.validate(new JsonBlob(json));
        result.writeTo(System.out);

        assertThat("should not pass because /paths is missing", result.failed());
    }

}