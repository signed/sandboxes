package com.github.signed.swagger.sort;

import static com.github.signed.swagger.essentials.Collectors2.toLinkedMap;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.github.signed.swagger.essentials.Parameters;
import com.github.signed.swagger.essentials.SwaggerStreams;

import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.Parameter;

public class SwaggerSort {

    private final Parameters parameters = new Parameters();
    private final SwaggerStreams swaggerStreams = new SwaggerStreams();
    private final Comparator<String> comparator = (o1, o2) -> o1.toLowerCase().compareTo(o2.toLowerCase());

    public Swagger sort(Swagger swagger) {
        swagger.setTags(sortedTags(swagger));
        swagger.setParameters(sortedParameterDefinitions(swagger));
        sortDefaultParametersInPathDefinitions(swagger);
        sortParametersInOperations(swagger);
        swagger.setDefinitions(sortedDefinitions(swagger));
        return swagger;
    }

    private void sortParametersInOperations(Swagger swagger) {
        swaggerStreams.operationsStream(swagger).forEach(operation -> operation.setParameters(sortedParameters(swagger, operation.getParameters())));
    }

    private void sortDefaultParametersInPathDefinitions(Swagger swagger) {
        swaggerStreams.pathsStream(swagger).forEach(path -> path.setParameters(sortedParameters(swagger, path.getParameters())));
    }

    private List<Parameter> sortedParameters(Swagger swagger, List<Parameter> parameters) {
        return sortByStringProperty(parameters, parameter -> {
            Parameter resolvedParameter = this.parameters.resolveRefParametersWithDefinitions(swagger, parameter);
            return new SortContainer<>(parameter, resolvedParameter.getName());
        });
    }

    private List<Tag> sortedTags(Swagger swagger) {
        return sortByStringProperty(swagger.getTags(), tag -> SortContainer.sortKeyFromProperty(tag, Tag::getName));
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

    private <T> List<T> sortByStringProperty(List<T> list, Function<T, SortContainer<T>> stringProperty) {
        if (null == list) {
            return null;
        }
        return list.stream().map(stringProperty).sorted((o1, o2) -> comparator.compare(o1.sortKey, o2.sortKey)).map(sortContainer -> sortContainer.item).collect(toList());
    }

    private static class SortContainer<Item> {

        public static <T> SortContainer<T> sortKeyFromProperty(T item, Function<T, String> extract) {
            return new SortContainer<>(item, extract.apply(item));
        }

        public final Item item;
        public final String sortKey;

        private SortContainer(Item item, String sortKey) {
            this.item = item;
            this.sortKey = sortKey;
        }
    }
}
