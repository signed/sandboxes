package com.github.signed.swagger.merge;

import static com.github.signed.swagger.trim.PathContainedInBooth.pathContainedInBooth;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.signed.swagger.essentials.SwaggerStreams;
import com.google.common.collect.Maps;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.util.Json;

public class SwaggerMerger {

    private final SwaggerStreams swaggerStreams = new SwaggerStreams();

    public Swagger merge(Swagger one, Swagger two) {
        LinkedHashMap<String, Path> mergedPaths = mergePathDefinitions(one, two);
        LinkedHashMap<String, Model> mergedDefinitions = mergedModelDefinitions(one, two);
        List<Tag> mergedTagDefinitions = mergedTagDefinitions(one, two);

        Swagger swagger = new Swagger();
        swagger.setPaths(mergedPaths.isEmpty() ? null : mergedPaths);
        swagger.setDefinitions(mergedDefinitions.isEmpty() ? null : mergedDefinitions);
        swagger.setTags(mergedTagDefinitions.isEmpty() ? null : mergedTagDefinitions);

        return swagger;
    }

    private LinkedHashMap<String, Path> mergePathDefinitions(Swagger one, Swagger two) {
        List<Pair<String, String>> conflictingPathDefinitions = ofNullable(one.getPaths()).orElse(emptyMap()).keySet().stream().
                filter(pathContainedInBooth(two))
                .map(serializeBothModelElementsToJson(one, two, (swagger, s) -> swagger.getPaths().get(s)))
                .filter(thoseWhoAreNotIdentical())
                .collect(toList());

        if (!conflictingPathDefinitions.isEmpty()) {
            throw new SwaggerMergeException("conflicting path definitions");
        }

        LinkedHashMap<String, Path> mergedPaths = new LinkedHashMap<>();
        mergedPaths.putAll(ofNullable(one.getPaths()).orElse(emptyMap()));
        mergedPaths.putAll(ofNullable(two.getPaths()).orElse(emptyMap()));
        return mergedPaths;
    }

    private LinkedHashMap<String, Model> mergedModelDefinitions(Swagger one, Swagger two) {
        List<Pair<String, String>> conflictingModelDefinition = swaggerStreams.definitions(one).keySet().stream().
                filter(definitionsContainedInBooth(two))
                .map(serializeBothModelElementsToJson(one, two, (swagger, s) -> swagger.getDefinitions().get(s)))
                .filter(thoseWhoAreNotIdentical())
                .collect(toList());

        if (!conflictingModelDefinition.isEmpty()) {
            throw new SwaggerMergeException("conflicting model definitions");
        }

        LinkedHashMap<String, Model> mergedDefinitions = new LinkedHashMap<>();
        mergedDefinitions.putAll(swaggerStreams.definitions(one));
        mergedDefinitions.putAll(swaggerStreams.definitions(two));
        return mergedDefinitions;
    }

    private List<Tag> mergedTagDefinitions(Swagger one, Swagger two) {
        Map<String, Tag> firstTagByName = swaggerStreams.tagsStream(one).collect(Collectors.toMap(Tag::getName, tag -> tag));
        Map<String, Tag> secondTagByName = swaggerStreams.tagsStream(two).collect(Collectors.toMap(Tag::getName, tag -> tag));
        List<Pair<String, String>> conflictingTagDefinitions = firstTagByName.keySet().stream().filter(secondTagByName::containsKey)
                .map(serializeBothModelElementsToJson(one, two, tagWithName()))
                .filter(thoseWhoAreNotIdentical())
                .collect(toList());

        if(!conflictingTagDefinitions.isEmpty()){
            throw new SwaggerMergeException("conflicting tag definitions");
        }

        Map<String, Tag> mergedTagDefinitions = Maps.newLinkedHashMap();
        swaggerStreams.tagsStream(one).forEach(tag -> mergedTagDefinitions.put(tag.getName(), tag));
        swaggerStreams.tagsStream(two).forEach(tag -> mergedTagDefinitions.put(tag.getName(), tag));

        return mergedTagDefinitions.values().stream().collect(toList());
    }

    private BiFunction<Swagger, String, Object> tagWithName() {
        return (swagger, s) -> swagger.getTags().stream()
                .filter(tag -> s.equals(tag.getName()))
                .findFirst().get();
    }

    private Predicate<Pair<String, String>> thoseWhoAreNotIdentical() {
        return jsonPair -> !jsonPair.getLeft().equals(jsonPair.getRight());
    }

    private Function<String, Pair<String, String>> serializeBothModelElementsToJson(Swagger one, Swagger two, BiFunction<Swagger, String, Object> extractor) {
        return identifier -> {
            Object _1st = extractor.apply(one, identifier);
            Object _2nd = extractor.apply(two, identifier);
            try {
                return Pair.of(Json.mapper().writeValueAsString(_1st), Json.mapper().writeValueAsString(_2nd));
            } catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
        };
    }

    private Predicate<String> definitionsContainedInBooth(Swagger two) {
        return modelDefinitionIdentifier -> swaggerStreams.definitions(two).containsKey(modelDefinitionIdentifier);
    }
}
