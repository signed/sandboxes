package com.github.signed.swagger.merge;

import static com.github.signed.swagger.trim.PathContainedInBooth.pathContainedInBooth;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.util.Json;

public class SwaggerMerger {
    public Swagger merge(Swagger one, Swagger two) {
        List<Pair<String, String>> conflictingPathDefinitions = ofNullable(one.getPaths()).orElse(emptyMap()).keySet().stream().
                filter(pathContainedInBooth(two))
                .map(serializeBothModelElementsToJson(one, two, (swagger, s) -> swagger.getPaths().get(s)))
                .filter(thoseWhoAreNotIdentical())
                .collect(toList());

        if (!conflictingPathDefinitions.isEmpty()) {
            throw new SwaggerMergeException("not matching");
        }

        LinkedHashMap<String, Path> mergedPaths = new LinkedHashMap<>();
        mergedPaths.putAll(ofNullable(one.getPaths()).orElse(emptyMap()));
        mergedPaths.putAll(ofNullable(two.getPaths()).orElse(emptyMap()));


        List<Pair<String, String>> conflictingModelDefinition = ofNullable(one.getDefinitions()).orElse(emptyMap()).keySet().stream().
                filter(definitionsContainedInBooth(two))
                .map(serializeBothModelElementsToJson(one, two, (swagger, s) -> swagger.getDefinitions().get(s)))
                .filter(thoseWhoAreNotIdentical())
                .collect(toList());

        if (!conflictingModelDefinition.isEmpty()) {
            throw new SwaggerMergeException("not matching");
        }

        LinkedHashMap<String, Model> mergedDefinitions = new LinkedHashMap<>();
        mergedDefinitions.putAll(ofNullable(one.getDefinitions()).orElse(emptyMap()));
        mergedDefinitions.putAll(ofNullable(two.getDefinitions()).orElse(emptyMap()));

        List<Tag> mergedTagDefinitions = new ArrayList<>();
        mergedTagDefinitions.addAll(ofNullable(one.getTags()).orElse(emptyList()));
        mergedTagDefinitions.addAll(ofNullable(two.getTags()).orElse(emptyList()));
        Swagger swagger = new Swagger();
        swagger.setPaths(mergedPaths.isEmpty() ? null : mergedPaths);
        swagger.setDefinitions(mergedDefinitions.isEmpty() ? null : mergedDefinitions);
        swagger.setTags(mergedTagDefinitions.isEmpty()?null:mergedTagDefinitions);

        return swagger;
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
        return modelDefinitionIdentifier -> Optional.ofNullable(two.getDefinitions()).orElse(Collections.emptyMap()).containsKey(modelDefinitionIdentifier);
    }

}
