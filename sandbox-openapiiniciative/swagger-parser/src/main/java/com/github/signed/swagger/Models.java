package com.github.signed.swagger;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import io.swagger.models.ArrayModel;
import io.swagger.models.ComposedModel;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

public class Models {

    private final Properties properties = new Properties();

    public List<DefinitionReference> definitionReferencesIn(Model model) {
        if (model instanceof RefModel) {
            return Collections.singletonList((DefinitionReference) ((RefModel) model)::getSimpleRef);
        } else if (model instanceof ComposedModel) {
            ComposedModel composedModel = (ComposedModel) model;
            return composedModel.getAllOf().stream().map(this::definitionReferencesIn).flatMap(List::stream).collect(toList());
        } else if (model instanceof ModelImpl) {
            ModelImpl modelImpl = (ModelImpl) model;
            return allPropertiesIn(modelImpl).stream().map(properties::definitionReferencesIn).flatMap(List::stream).collect(toList());
        } else if (model instanceof ArrayModel) {
            ArrayModel arrayModel = (ArrayModel) model;
            return properties.definitionReferencesIn(arrayModel.getItems());
        } else {
            throw new RuntimeException("still work to do");
        }
    }

    private List<Property> allPropertiesIn(ModelImpl modelImpl) {
        Collection<Property> properties = Optional.ofNullable(modelImpl.getProperties()).orElse(Collections.emptyMap()).values();
        Optional<Property> maybeAnAdditionalProperty = Optional.ofNullable(modelImpl.getAdditionalProperties());
        List<Property> allProperties = Lists.newArrayList();
        allProperties.addAll(properties);
        maybeAnAdditionalProperty.ifPresent(allProperties::add);
        return allProperties;
    }

}
