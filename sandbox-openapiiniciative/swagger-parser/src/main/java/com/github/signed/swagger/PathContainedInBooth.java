package com.github.signed.swagger;

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
        String variable_name = url.replaceAll("\\{[^\\}]+\\}", "{variable}");
        System.out.println(variable_name);
        return variable_name;
    }
}
