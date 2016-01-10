package com.github.signed.swagger;

import java.util.LinkedHashMap;

import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class Merger {
    public Swagger merge(Swagger one, Swagger two) {
        LinkedHashMap<String, Path> mergedPaths = new LinkedHashMap<>();
        mergedPaths.putAll(one.getPaths());
        mergedPaths.putAll(two.getPaths());
        Swagger swagger = new Swagger();
        swagger.setPaths(mergedPaths);
        return swagger;
    }
}
