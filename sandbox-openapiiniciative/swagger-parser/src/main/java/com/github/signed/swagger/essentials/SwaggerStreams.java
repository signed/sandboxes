package com.github.signed.swagger.essentials;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

import java.util.Map;
import java.util.stream.Stream;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;

public class SwaggerStreams {

    public Stream<Operation> operations(Swagger swagger) {
        return paths(swagger).flatMap(path -> path.getOperations().stream());
    }

    public Stream<Path> paths(Swagger swagger) {
        return ofNullable(swagger.getPaths()).orElse(emptyMap()).values().stream();
    }

    public Map<String, Response> responses(Swagger swagger) {
        return ofNullable(swagger.getResponses()).orElse(emptyMap());
    }
}
