package com.github.signed.swagger;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class PathContainedInBooth_Test {

    private final SwaggerBuilder other = SwaggerMother.emptyApiDefinition();

    @Test
    public void url_templating_with_different_templating_variable_names_but_otherwise_identical() throws Exception {
        other.withPath("/{name}/constant").withPost();
        assertThat("differently url template names still refer to the same endpoint", PathContainedInBooth.pathContainedInBooth(other.build()).test("/{anothername}/constant"));
    }
}