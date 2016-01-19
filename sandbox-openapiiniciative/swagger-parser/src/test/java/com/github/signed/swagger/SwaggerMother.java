package com.github.signed.swagger;

public class SwaggerMother {
    public static SwaggerBuilder emptyApiDefinition() {
        SwaggerBuilder builder = new SwaggerBuilder();
        builder.withInfo().withTitle("any title").withVersion("any version");
        return builder;
    }

    public static SwaggerBuilder mergedSwaggerDescription() {
        return emptyApiDefinition();
    }
}
