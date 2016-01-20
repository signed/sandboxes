package com.github.signed.swagger;

import static com.github.signed.swagger.SwaggerMatcher.hasDefinitionsFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Collections;

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
        path.withParameterForAllOperations(ParameterMother.anyParameterReferencingModelDefinition("referenced-in-path"));
        ModelBuilder referencedInPath = swaggerBuilder.withModelDefinition("referenced-in-path").withTypeObject();
        referencedInPath.withReferencePropertyNamed("some-property").withReferenceTo("not-referenced-in-a-path");
        swaggerBuilder.withModelDefinition("not-referenced-in-a-path").withTypeObject();

        assertThat(trimmed(), hasDefinitionsFor("not-referenced-in-a-path"));
    }

    @Test
    public void remove_empty_tag_lists_in_path_operations() throws Exception {
        swaggerBuilder.withPath("/").withPost();
        Swagger swagger = swaggerBuilder.build();
        swagger.getPath("/").getPost().setTags(Collections.emptyList());

        assertThat(trimmed(swagger).getPath("/").getPost().getTags(), nullValue());
    }

    private Swagger trimmed() {
        Swagger build = swaggerBuilder.build();
        Json.prettyPrint(build);
        Swagger trim = trimmed(build);
        Yaml.prettyPrint(trim);
        return trim;
    }

    private Swagger trimmed(Swagger build) {
        return new SwaggerTrim().trim(build);
    }
}