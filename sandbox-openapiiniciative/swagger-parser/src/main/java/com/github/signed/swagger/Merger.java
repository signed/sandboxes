package com.github.signed.swagger;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

import java.util.LinkedHashMap;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class Merger {
    public Swagger merge(Swagger one, Swagger two) {
        LinkedHashMap<String, Path> mergedPaths = new LinkedHashMap<>();
        mergedPaths.putAll(ofNullable(one.getPaths()).orElse(emptyMap()));
        mergedPaths.putAll(ofNullable(two.getPaths()).orElse(emptyMap()));

        LinkedHashMap<String, Model> mergedDefinitions = new LinkedHashMap<>();
        mergedDefinitions.putAll(ofNullable(one.getDefinitions()).orElse(emptyMap()));
        mergedDefinitions.putAll(ofNullable(two.getDefinitions()).orElse(emptyMap()));

        Swagger swagger = new Swagger();
        swagger.setPaths(mergedPaths.isEmpty() ? null : mergedPaths);
        swagger.setDefinitions(mergedDefinitions.isEmpty() ? null : mergedDefinitions);

        return swagger;
    }
}
