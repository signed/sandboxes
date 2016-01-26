package com.github.signed.swagger.sort;

import static com.github.signed.swagger.essentials.ParameterMother.anyParameter;
import static com.github.signed.swagger.essentials.TagMatcher.tagNamed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.github.signed.swagger.essentials.SwaggerBuilder;
import com.github.signed.swagger.essentials.SwaggerMother;

import io.swagger.models.Swagger;
import io.swagger.models.Tag;

public class SwaggerSort_Test {

    private final List<String> unordered = Arrays.asList("zebra", "ape", "Ant", "aaa");
    private final List<String> ordered = Arrays.asList("aaa", "Ant", "ape", "zebra");
    private final SwaggerBuilder builder = SwaggerMother.emptyApiDefinition();

    @Test
    public void sort_tags_by_case_insensitive_name() throws Exception {
        unordered.forEach(builder::defineTag);
        Iterator<Tag> tags = sort().getTags().iterator();

        ordered.forEach(tag -> assertThat(tags.next(), is(tagNamed(tag))));
    }

    @Test
    public void sort_model_definitions_by_case_insensitive_identifier() throws Exception {
        unordered.forEach(builder::withModelDefinition);
        Iterator<String> definitions = sort().getDefinitions().keySet().iterator();

        ordered.forEach(identifier -> assertThat(definitions.next(), is(identifier)));
    }

    @Test
    public void sort_parameter_definitions_by_case_insensitive_identifier() throws Exception {
        unordered.forEach((parameterIdentifier) -> builder.withParameterDefinition(parameterIdentifier, anyParameter()));
        Iterator<String> parameterDefinitions = sort().getParameters().keySet().iterator();

        ordered.forEach(identifier -> assertThat(parameterDefinitions.next(), is(identifier)));
    }

    private Swagger sort() {
        return new SwaggerSort().sort(builder.build());
    }
}
