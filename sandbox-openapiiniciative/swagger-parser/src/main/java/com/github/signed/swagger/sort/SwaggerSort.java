package com.github.signed.swagger.sort;

import static com.github.signed.swagger.essentials.Collectors2.toLinkedMap;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.github.signed.swagger.essentials.SwaggerStreams;

import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.Parameter;

public class SwaggerSort {

    private final SwaggerStreams swaggerStreams = new SwaggerStreams();
    private final Comparator<String> comparator = (o1, o2) -> o1.toLowerCase().compareTo(o2.toLowerCase());

    public Swagger sort(Swagger swagger) {
        swagger.setTags(sortedTags(swagger));
        swagger.setParameters(sortedParameterDefinitions(swagger));
        sortDefaultParametersInPath(swagger);
        swagger.setDefinitions(sortedDefinitions(swagger));
        return swagger;
    }

    private void sortDefaultParametersInPath(Swagger swagger) {
        swaggerStreams.pathsStream(swagger).forEach(path -> path.setParameters(sortedParameters(path.getParameters())));
    }

    private List<Parameter> sortedParameters(List<Parameter> parameters) {
        if(null == parameters){
            return null;
        }
        return parameters.stream().sorted(((o1, o2) -> comparator.compare(o1.getName(), o2.getName()))).collect(toList());
    }

    private Map<String, Parameter> sortedParameterDefinitions(Swagger swagger) {
        return sortByKey(swagger.getParameters());
    }

    private Map<String, Model> sortedDefinitions(Swagger swagger) {
        return sortByKey(swagger.getDefinitions());
    }

    private <T> Map<String, T> sortByKey(Map<String, T> map) {
        if (null == map) {
            return null;
        }
        return map.entrySet().stream().sorted((o1, o2) -> comparator.compare(o1.getKey(), o2.getKey())).collect(toLinkedMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Tag> sortedTags(Swagger swagger) {
        if (null == swagger.getTags()) {
            return null;
        }
        return swagger.getTags().stream().sorted((o1, o2) -> comparator.compare(o1.getName(), o2.getName())).collect(toList());
    }
}
