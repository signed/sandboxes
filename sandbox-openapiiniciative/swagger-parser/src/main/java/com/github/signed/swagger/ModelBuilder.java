package com.github.signed.swagger;

import static java.util.stream.Collectors.toMap;

import java.util.Map;

import com.google.common.collect.Maps;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.Property;

public class ModelBuilder {
    private final Map<String, PropertyBuilder> propertyBuilders = Maps.newLinkedHashMap();
    private String type;

    public ModelBuilder withTypeObject() {
        type = "object";
        return this;
    }

    public ModelBuilder withTypeString() {
        type = "string";
        return this;
    }

    public RefPropertyBuilder withReferencePropertyNamed(String propertyName){
        RefPropertyBuilder builder = new RefPropertyBuilder();
        propertyBuilders.put(propertyName, builder);
        return builder;
    }

    public StringPropertyBuilder withStringPropertyNamed(String propertyName) {
        StringPropertyBuilder propertyBuilder = new StringPropertyBuilder();
        propertyBuilders.put(propertyName, propertyBuilder);
        return propertyBuilder;
    }

    public Model build() {
        ModelImpl model = new ModelImpl();
        model.setType(type);
        Map<String, Property> properties = propertyBuilders.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().build()));
        model.setProperties(properties);
        return model;
    }
}
