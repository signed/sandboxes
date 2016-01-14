package com.github.signed.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class Merger {
    public Swagger merge(Swagger one, Swagger two) {
        List<Pair<String, String>> conflictingPathDefinitions = ofNullable(one.getPaths()).orElse(emptyMap()).keySet().stream().
                filter(pathContainedInBooth(two))
                .map(serializeBothModelElementsToJson(one, two, (swagger, s) -> swagger.getPaths().get(s)))
                .filter(thoseWhoAreNotIdentical())
                .collect(toList());

        if (!conflictingPathDefinitions.isEmpty()) {
            throw new RuntimeException("not matching");
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
            throw new RuntimeException("not matching");
        }

        LinkedHashMap<String, Model> mergedDefinitions = new LinkedHashMap<>();
        mergedDefinitions.putAll(ofNullable(one.getDefinitions()).orElse(emptyMap()));
        mergedDefinitions.putAll(ofNullable(two.getDefinitions()).orElse(emptyMap()));

        Swagger swagger = new Swagger();
        swagger.setPaths(mergedPaths.isEmpty() ? null : mergedPaths);
        swagger.setDefinitions(mergedDefinitions.isEmpty() ? null : mergedDefinitions);

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

    private Predicate<String> pathContainedInBooth(Swagger two){
        return exposedPath -> two.getPaths().containsKey(exposedPath);
    }

    private Predicate<String> definitionsContainedInBooth(Swagger two) {
        return modelDefinitionIdentifier -> two.getDefinitions().containsKey(modelDefinitionIdentifier);
    }
}
