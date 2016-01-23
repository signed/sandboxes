package com.github.signed.swagger.essentials;

import java.util.List;

import com.github.signed.swagger.trim.DefinitionReference;

import io.swagger.models.Swagger;

public interface DefinitionReferenceProbe {
    List<DefinitionReference> retrieveReferencesIn(Swagger swagger);
}
