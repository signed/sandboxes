package com.github.signed.swagger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;

public class Properties {

    private static Function<Property, List<DefinitionReference>> containsNoDefinitionReferences() {
        return property -> Collections.emptyList();
    }

    private Function<Property, List<DefinitionReference>> NotClearHowToHandle() {
        return property -> {
            throw new UnsupportedOperationException();
        };
    }

    private final Map<Class<?>, Function<Property, List<DefinitionReference>>> definitionReference = Maps.newHashMap();

    {
        definitionReference.put(RefProperty.class, property -> Collections.singletonList((DefinitionReference) ((RefProperty) property)::getSimpleRef));
        definitionReference.put(ArrayProperty.class, property -> definitionReferencesIn(((ArrayProperty) property).getItems()));
        definitionReference.put(MapProperty.class, NotClearHowToHandle());
    }

    public List<DefinitionReference> definitionReferencesIn(Property property) {
        Class<? extends Property> aClass = property.getClass();
        return definitionReference.getOrDefault(aClass, containsNoDefinitionReferences()).apply(property);
    }
}
