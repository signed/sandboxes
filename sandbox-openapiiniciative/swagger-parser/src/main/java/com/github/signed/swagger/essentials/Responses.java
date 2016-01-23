package com.github.signed.swagger.essentials;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.github.signed.swagger.trim.ResponseReference;
import com.github.signed.swagger.trim.DefinitionReference;

import io.swagger.models.RefResponse;
import io.swagger.models.Response;
import io.swagger.models.properties.Property;

public class Responses {
    private final Properties properties = new Properties();

    public List<DefinitionReference> definitionReferencesIn(Response response) {
        Optional<Property> maybeSchema = Optional.ofNullable(response.getSchema());
        if (maybeSchema.isPresent()) {
            return properties.definitionReferencesIn(response.getSchema());
        }
        return Collections.emptyList();
    }

    public List<ResponseReference> responseReferencesIn(Response response) {
        if (response instanceof RefResponse) {
            return Collections.singletonList(new ResponseReference(((RefResponse) response)));
        }
        return Collections.emptyList();
    }
}
