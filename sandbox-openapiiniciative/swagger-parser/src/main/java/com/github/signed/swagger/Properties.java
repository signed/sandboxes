package com.github.signed.swagger;

import java.util.Collections;
import java.util.List;

import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;

public class Properties {

    public List<DefinitionReference> definitionReferencesIn(Property property) {
        if (property instanceof RefProperty) {
            return Collections.singletonList((DefinitionReference) ((RefProperty) property)::getSimpleRef);
        }

        if(property instanceof StringProperty || property instanceof IntegerProperty){
            return Collections.emptyList();
        }

        throw new RuntimeException("Definition extraction from properties still needs work");
    }
}
