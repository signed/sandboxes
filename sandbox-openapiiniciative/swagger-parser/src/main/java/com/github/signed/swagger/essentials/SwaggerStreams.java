package com.github.signed.swagger.essentials;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

import java.util.Map;
import java.util.stream.Stream;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;

public class SwaggerStreams {

    public Stream<Operation> operationsStream(Swagger swagger) {
        return pathsStream(swagger).flatMap(path -> path.getOperations().stream());
    }

    public Stream<Path> pathsStream(Swagger swagger) {
        return ofNullable(swagger.getPaths()).orElse(emptyMap()).values().stream();
    }

    public Stream<Tag> tagsStream(Swagger one) {
        return ofNullable(one.getTags()).orElse(emptyList()).stream();
    }

    public Map<String, Response> responses(Swagger swagger) {
        return ofNullable(swagger.getResponses()).orElse(emptyMap());
    }
}
