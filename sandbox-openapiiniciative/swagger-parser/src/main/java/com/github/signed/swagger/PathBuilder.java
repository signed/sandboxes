package com.github.signed.swagger;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.function.Consumer;

import io.swagger.models.Path;

public class PathBuilder {

    private final List<Consumer<Path>> operations = newArrayList();

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

    public Path build() {
        Path path = new Path();
        operations.stream().forEach(operation -> operation.accept(path));
        return path;
    }
}
