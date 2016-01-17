package com.github.signed.swagger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.models.Operation;
import io.swagger.models.Response;
import io.swagger.models.properties.Property;

public class Operations {
    private final Properties properties = new Properties();

    public List<DefinitionReference> definitionReferencesIn(Operation operation) {
        return Optional.ofNullable(operation.getResponses()).orElse(Collections.emptyMap()).values().stream().map(this::definitionReferencesIn).flatMap(List::stream).collect(Collectors.toList());
    }

    private List<DefinitionReference> definitionReferencesIn(Response response) {
        Optional<Property> maybeSchema = Optional.ofNullable(response.getSchema());
        if (maybeSchema.isPresent()) {
            return properties.definitionReferencesIn(response.getSchema());
        }
        return Collections.emptyList();
    }
}
