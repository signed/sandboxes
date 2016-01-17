package com.github.signed.swagger;

import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class SwaggerReduce {
    public static SwaggerReduce publicAsMarkerTag() {
        return new SwaggerReduce("public");
    }

    private final String markerTag;

    public SwaggerReduce(String markerTag) {
        this.markerTag = markerTag;
    }

    public Swagger reduce(Swagger swagger) {
        Map<String, Path> pathsWithTaggedOperations = swagger.getPaths().entrySet().stream()
                .map(removeOperationsWithoutMarkerTag())
                .filter(pathsThatStillHaveOperations())
                .map(removeMarkerTagFromOperations())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        swagger.setPaths(pathsWithTaggedOperations);
        return swagger;
    }

    private Function<Map.Entry<String, Path>, Map.Entry<String, Path>> removeOperationsWithoutMarkerTag() {
        return pathsEntry -> {
            removeOperationsWithoutMarkerTagFrom(pathsEntry.getValue());
            return pathsEntry;
        };
    }

    private void removeOperationsWithoutMarkerTagFrom(Path path) {
        path.getOperationMap().entrySet().stream()
                .filter(operationsEntry -> !hasTag(operationsEntry.getValue()))
                .map(Map.Entry::getKey)
                .forEach(httpMethod -> path.set(httpMethod.name().toLowerCase(), null));
    }

    private boolean hasTag(Operation value) {
        return Optional.ofNullable(value.getTags()).orElse(Collections.emptyList()).contains(markerTag);
    }

    private Predicate<Map.Entry<String, Path>> pathsThatStillHaveOperations() {
        return pathsEntry -> !pathsEntry.getValue().isEmpty();
    }

    private Function<Map.Entry<String, Path>, Map.Entry<String, Path>> removeMarkerTagFromOperations() {
        return pathsEntry -> {
            pathsEntry.getValue().getOperations().stream().forEach(operation -> Optional.ofNullable(operation.getTags()).ifPresent(tags -> tags.remove(markerTag)));
            return pathsEntry;
        };
    }
}
