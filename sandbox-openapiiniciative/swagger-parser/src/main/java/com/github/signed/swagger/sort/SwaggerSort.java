package com.github.signed.swagger.sort;

import static com.github.signed.swagger.essentials.Collectors2.toLinkedMap;
import static java.util.Optional.ofNullable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.Parameter;

public class SwaggerSort {

    private final Comparator<String> comparator = (o1, o2) -> o1.toLowerCase().compareTo(o2.toLowerCase());

    public Swagger sort(Swagger swagger){
        swagger.setTags(sortedTags(swagger));
        swagger.setParameters(sortedParameters(swagger));
        swagger.setDefinitions(sortDefinitions(swagger));
        return swagger;
    }

    private Map<String, Parameter> sortedParameters(Swagger swagger) {
        if( null == swagger.getParameters()){
            return null;
        }
        return swagger.getParameters().entrySet().stream().sorted((o1, o2) -> comparator.compare(o1.getKey(), o2.getKey())).collect(toLinkedMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Model> sortDefinitions(Swagger swagger) {
        if( null == swagger.getDefinitions()){
            return null;
        }
        return swagger.getDefinitions().entrySet().stream().sorted((o1, o2) -> comparator.compare(o1.getKey(), o2.getKey())).collect(toLinkedMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Tag> sortedTags(Swagger swagger) {
        Optional<List<Tag>> maybeTags = ofNullable(swagger.getTags());
        if(!maybeTags.isPresent()){
            return null;
        }
        return maybeTags.get().stream().sorted((o1, o2) -> comparator.compare(o1.getName(), o2.getName())).collect(Collectors.toList());
    }
}
