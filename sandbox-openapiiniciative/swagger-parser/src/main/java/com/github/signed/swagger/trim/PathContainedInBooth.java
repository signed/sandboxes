package com.github.signed.swagger.trim;

import io.swagger.models.Swagger;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PathContainedInBooth {

    public static Predicate<String> pathContainedInBooth(Swagger two){
        return exposedPath -> two.getPaths().keySet().stream()
                .map(PathContainedInBooth::unifyUrlTemplateVariableNames)
                .collect(Collectors.toList())
                .contains(unifyUrlTemplateVariableNames(exposedPath));
    }

    private static String unifyUrlTemplateVariableNames(String url) {
        return url.replaceAll("\\{[^\\}]+\\}", "{variable}");
    }
}
