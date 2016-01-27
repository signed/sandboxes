package com.github.signed.swagger.essentials;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.function.Consumer;

import io.swagger.models.Path;

public class PathBuilder {

    private final List<Consumer<Path>> operations = newArrayList();
    private final List<ParameterBuilder> parameters = newArrayList();

    public OperationBuilder withOption() {
        OperationBuilder builder = new OperationBuilder();
        operations.add(path -> path.set("options", builder.build()));
        return builder;
    }

    public OperationBuilder withPost() {
        OperationBuilder builder = new OperationBuilder();
        operations.add(path -> path.set("post", builder.build()));
        return builder;
    }

    public ParameterBuilder withParameterForAllOperations() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        withParameterForAllOperations(parameterBuilder);
        return parameterBuilder;
    }

    public PathBuilder withParameterForAllOperations(String parameterName, ParameterBuilder parameterBuilder) {
        parameterBuilder.withName(parameterName);
        return withParameterForAllOperations(parameterBuilder);
    }

    public PathBuilder withParameterForAllOperations(ParameterBuilder parameterBuilder) {
        parameters.add(parameterBuilder);
        return this;
    }

    public Path build() {
        Path path = new Path();
        operations.stream().forEach(operation -> operation.accept(path));
        parameters.stream().forEach(parameterBuilder -> path.addParameter(parameterBuilder.build()));
        return path;
    }
}
