package com.github.signed.swagger;

import java.util.List;

import io.swagger.models.Swagger;

public interface DefinitionReferenceProbe {
    List<DefinitionReference> retrieveReferencesIn(Swagger swagger);
}
