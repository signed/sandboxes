package com.github.signed.swagger.merge;

import org.junit.Test;

import com.github.signed.swagger.essentials.SwaggerMother;
import com.github.signed.swagger.essentials.SwaggerBuilder;

public class SwaggerMerger_Test {

    private final SwaggerBuilder first = SwaggerMother.emptyApiDefinition();
    private final SwaggerBuilder second = SwaggerMother.emptyApiDefinition();
    private final SwaggerMerger swaggerMerger = new SwaggerMerger();

    @Test(expected = SwaggerMergeException.class)
    public void same_path_but_different_operations() throws Exception {
        first.withPath("/{variable}/constant").withPost();
        second.withPath("/{argument}/constant").withOption();
        swaggerMerger.merge(first.build(), second.build());
    }

}