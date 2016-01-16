package com.github.signed.swagger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import io.swagger.models.Swagger;
import io.swagger.util.Json;
import io.swagger.util.Yaml;

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

    @Test
    public void do_not_remove_a_model_that_is_referenced_in_another_model_that_is_actually_referenced() throws Exception {
        PathBuilder path = swaggerBuilder.withPath("/");
        path.withPost();
        path.withParameterForAllOperations().withReferenceToSchemaDefinition("referenced-in-path");
        ModelBuilder referencedInPath = swaggerBuilder.withModelDefinition("referenced-in-path").withTypeObject();
        referencedInPath.withReferencePropertyNamed("some-property").withReferenceTo("not-referenced-in-a-path");
        swaggerBuilder.withModelDefinition("not-referenced-in-a-path").withTypeObject();

        assertThat(trimmed(), SwaggerMatcher.hasDefinitionsFor("not-referenced-in-a-path"));
    }

    private Swagger trimmed() {
        Swagger build = swaggerBuilder.build();
        Json.prettyPrint(build);
        Swagger trim = new SwaggerTrim().trim(build);
        Yaml.prettyPrint(trim);
        return trim;
    }
}