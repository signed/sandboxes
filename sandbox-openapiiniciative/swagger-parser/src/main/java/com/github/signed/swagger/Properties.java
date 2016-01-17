package com.github.signed.swagger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;

import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;

public class Properties {

    private static Function<Property, List<DefinitionReference>> containsNoDefinitionReferences() {
        return property -> Collections.emptyList();
    }

    private final Map<Class<?>, Function<Property, List<DefinitionReference>>> definitionReference = Maps.newHashMap();

    {
        definitionReference.put(RefProperty.class, property -> Collections.singletonList((DefinitionReference) ((RefProperty) property)::getSimpleRef));
    }

    public List<DefinitionReference> definitionReferencesIn(Property property) {
        return definitionReference.getOrDefault(property.getClass(), containsNoDefinitionReferences()).apply(property);
    }
}
