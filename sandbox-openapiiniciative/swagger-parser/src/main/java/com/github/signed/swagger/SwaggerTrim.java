package com.github.signed.swagger;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Sets;

import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.Parameter;

public class SwaggerTrim {

    private final Parameters parameters = new Parameters();
    private final Responses responses = new Responses();
    private final Models models = new Models();

    public Swagger trim(Swagger swagger) {
        nullEmptyTagLists(swagger);
        removeNotReferencedTagsIn(swagger);
        removeNotReferencedParameterDefinitions(swagger);
        removeNotReferencedResponseDefinitions(swagger);
        removeNotReferencedModelDefinitionsIn(swagger);
        return swagger;
    }

    private void nullEmptyTagLists(Swagger swagger) {
        operations(swagger)
                .filter(operation -> ofNullable(operation.getTags()).orElse(emptyList()).isEmpty())
                .forEach(operation -> operation.setTags(null));
    }

    private void removeNotReferencedParameterDefinitions(Swagger swagger) {
        Set<String> parametersReferencedInOperations = operations(swagger)
                .flatMap(operation -> ofNullable(operation.getParameters()).orElse(emptyList()).stream())
                .map(parameters::parameterReferencesIn)
                .flatMap(List::stream).map(ParameterReference::parameterIdentifier).collect(toSet());
        Set<String> parametersReferencedInPath = paths(swagger)
                .flatMap(path -> ofNullable(path.getParameters()).orElse(Collections.emptyList()).stream())
                .map(parameters::parameterReferencesIn)
                .flatMap(List::stream).map(ParameterReference::parameterIdentifier).collect(toSet());

        Set<String> allParameterReferences = Sets.newHashSet();
        allParameterReferences.addAll(parametersReferencedInOperations);
        allParameterReferences.addAll(parametersReferencedInPath);
        Map<String, Parameter> referencedParameters = ofNullable(swagger.getParameters()).orElse(emptyMap()).entrySet().stream()
                .filter(stringParameterEntry -> allParameterReferences.contains(stringParameterEntry.getKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        swagger.setParameters(referencedParameters.isEmpty() ? null : referencedParameters);
    }

    private void removeNotReferencedResponseDefinitions(Swagger swagger) {
        Set<String> responseReferencesInOperations = operations(swagger)
                .flatMap(operation -> ofNullable(operation.getResponses()).orElse(emptyMap()).values().stream())
                .map(responses::responseReferencesIn)
                .flatMap(List::stream).map(ResponseReference::responseIdentifier).collect(toSet());
        Map<String, Response> referencedResponses = ofNullable(swagger.getResponses()).orElse(emptyMap()).entrySet().stream()
                .filter(stringResponseEntry -> responseReferencesInOperations.contains(stringResponseEntry.getKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        swagger.setResponses(responseReferencesInOperations.isEmpty() ? null : referencedResponses);
    }

    private Stream<Path> paths(Swagger swagger) {
        return ofNullable(swagger.getPaths()).orElse(emptyMap()).values().stream();
    }

    private Stream<Operation> operations(Swagger swagger) {
        return paths(swagger).flatMap(path -> path.getOperations().stream());
    }

    private void removeNotReferencedTagsIn(Swagger swagger) {
        Set<String> tagReferences = paths(swagger).map(allTagsReferencedInPath()).flatMap(Set::stream).collect(toSet());
        List<Tag> referencedTagDefinitions = ofNullable(swagger.getTags()).orElse(emptyList()).stream().filter(tag -> tagReferences.contains(tag.getName())).collect(Collectors.toList());
        swagger.setTags((referencedTagDefinitions.isEmpty()) ? null : referencedTagDefinitions);
    }

    private void removeNotReferencedModelDefinitionsIn(Swagger swagger) {
        Set<String> definitionsReferencedInParameters = operations(swagger).flatMap(operation -> operation.getParameters().stream())
                .map(parameters::definitionReferencesIn).flatMap(List::stream)
                .map(DefinitionReference::getSimpleRef).collect(toSet());

        Set<String> definitionReferencesInPathsDefaultParameters = paths(swagger).map(path -> ofNullable(path.getParameters()).orElse(emptyList())).flatMap(List::stream)
                .map(parameters::definitionReferencesIn).flatMap(List::stream)
                .map(DefinitionReference::getSimpleRef).collect(toSet());

        Set<String> definitionReferencesInParameterDefinitions = ofNullable(swagger.getParameters()).orElse(emptyMap()).values().stream()
                .map(parameters::definitionReferencesIn).flatMap(List::stream)
                .map(DefinitionReference::getSimpleRef).collect(toSet());

        Set<String> definitionReferencesInResponses = operations(swagger).flatMap(operation -> ofNullable(operation.getResponses()).orElse(emptyMap()).values().stream())
                .map(responses::definitionReferencesIn).flatMap(List::stream)
                .map(DefinitionReference::getSimpleRef).collect(toSet());

        Set<String> definitionReferencesInResponseDefinitions = ofNullable(swagger.getResponses()).orElse(emptyMap()).values().stream()
                .map(responses::definitionReferencesIn).flatMap(List::stream)
                .map(DefinitionReference::getSimpleRef).collect(Collectors.toSet());

        boolean keepRemoving = true;
        while (keepRemoving) {
            Set<String> definitionReferencesInDefinitions = ofNullable(swagger.getDefinitions()).orElse(emptyMap()).values().stream()
                    .map(models::definitionReferencesIn)
                    .flatMap(List::stream)
                    .map(DefinitionReference::getSimpleRef)
                    .collect(toSet());


            Set<String> allReferenceDefinitions = Sets.newHashSet();
            allReferenceDefinitions.addAll(definitionReferencesInDefinitions);
            allReferenceDefinitions.addAll(definitionReferencesInPathsDefaultParameters);
            allReferenceDefinitions.addAll(definitionReferencesInParameterDefinitions);
            allReferenceDefinitions.addAll(definitionsReferencedInParameters);
            allReferenceDefinitions.addAll(definitionReferencesInResponses);
            allReferenceDefinitions.addAll(definitionReferencesInResponseDefinitions);

            Map<String, Model> referencedDefinitions = ofNullable(swagger.getDefinitions()).orElse(emptyMap()).entrySet().stream()
                    .filter(stringModelEntry -> allReferenceDefinitions.contains(stringModelEntry.getKey()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

            keepRemoving = ofNullable(swagger.getDefinitions()).orElse(emptyMap()).size() > referencedDefinitions.size();
            swagger.setDefinitions(referencedDefinitions.isEmpty() ? null : referencedDefinitions);
        }
    }

    private Function<Path, Set<String>> allTagsReferencedInPath() {
        return path -> path.getOperations().stream()
                .map(Operation::getTags)
                .map(strings -> (null == strings) ? Collections.<String>emptyList() : strings)
                .flatMap(List::stream)
                .collect(toSet());
    }

    private Collection<Path> pathDefinitionsFrom(Swagger swagger) {
        return ofNullable(swagger.getPaths()).orElse(emptyMap()).values();
    }
}