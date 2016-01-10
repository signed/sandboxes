package com.github.signed.swagger;

public class SwaggerMother {
    public static SwaggerBuilder emptyApiDefinition() {
        return new SwaggerBuilder();
    }

    public static SwaggerBuilder mergedSwaggerDescription() {
        return emptyApiDefinition();
    }
}
