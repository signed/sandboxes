package com.github.signed.swagger.sort;

import static com.github.signed.swagger.essentials.TagMatcher.tagNamed;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Iterator;

import org.junit.Test;

import com.github.signed.swagger.essentials.SwaggerBuilder;
import com.github.signed.swagger.essentials.SwaggerMother;

import io.swagger.models.Swagger;
import io.swagger.models.Tag;

public class SwaggerSort_Test {
    private final SwaggerBuilder builder = SwaggerMother.emptyApiDefinition();

    @Test
    public void sort_tags_by_case_insensitive_name() throws Exception {
        builder.defineTag("zebra");
        builder.defineTag("ape");
        builder.defineTag("Ant");
        builder.defineTag("aaa");

        Swagger sorted = sort();

        Iterator<Tag> tags = sorted.getTags().iterator();
        assertThat(tags.next(), tagNamed("aaa"));
        assertThat(tags.next(), tagNamed("Ant"));
        assertThat(tags.next(), tagNamed("ape"));
        assertThat(tags.next(), tagNamed("zebra"));
    }

    private Swagger sort() {
        return new SwaggerSort().sort(builder.build());
    }
}
