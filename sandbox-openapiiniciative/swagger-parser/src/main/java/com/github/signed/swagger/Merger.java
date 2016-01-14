package com.github.signed.swagger;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.util.Json;

public class Merger {
    public Swagger merge(Swagger one, Swagger two) {
        LinkedHashMap<String, Path> mergedPaths = new LinkedHashMap<>();
        mergedPaths.putAll(ofNullable(one.getPaths()).orElse(emptyMap()));
        mergedPaths.putAll(ofNullable(two.getPaths()).orElse(emptyMap()));


        List<Pair<String, String>> notMatching = ofNullable(one.getDefinitions()).orElse(emptyMap()).keySet().stream().
                filter(definitionsContainedInBooth(two))
                .map(serializeBothModelElementsToJson(one, two))
                .filter(thoseWhoAreNotIdentical())
                .collect(toList());

        if (!notMatching.isEmpty()) {
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

    private Function<String, Pair<String, String>> serializeBothModelElementsToJson(Swagger one, Swagger two) {
        return s -> {
            Model _1st = one.getDefinitions().get(s);
            Model _2nd = two.getDefinitions().get(s);
            try {
                return Pair.of(Json.mapper().writeValueAsString(_1st), Json.mapper().writeValueAsString(_2nd));
            } catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
        };
    }

    private Predicate<String> definitionsContainedInBooth(Swagger two) {
        return s -> two.getDefinitions().containsKey(s);
    }
}
