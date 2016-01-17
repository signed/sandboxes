package com.github.signed.swagger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;

import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;

public class Properties {

    private static Function<Property, List<DefinitionReference>> containsNoDefinitionReferences() {
        return property -> Collections.emptyList();
    }

    private final Map<Class<?>, Function<Property, List<DefinitionReference>>> definitionReference = Maps.newHashMap();

    {
        definitionReference.put(StringProperty.class, containsNoDefinitionReferences());
        definitionReference.put(IntegerProperty.class, containsNoDefinitionReferences());
        definitionReference.put(RefProperty.class, property -> Collections.singletonList((DefinitionReference) ((RefProperty) property)::getSimpleRef));
    }

    public List<DefinitionReference> definitionReferencesIn(Property property) {
        return definitionReference.get(property.getClass()).apply(property);
    }
}
