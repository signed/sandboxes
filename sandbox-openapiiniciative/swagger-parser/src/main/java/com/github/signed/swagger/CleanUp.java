package com.github.signed.swagger;

import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class CleanUp {

    public Swagger cleanup(Swagger merged) {
        String markerTag = "public";

        Map<String, Path> aPublic = merged.getPaths().entrySet().stream()
                .filter(stringPathEntry -> stringPathEntry.getValue().getOptions().getTags().contains(markerTag))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Path path : aPublic.values()) {
            path.getOptions().getTags().remove(markerTag);
        }
        merged.setPaths(aPublic);
        return merged;
    }
}
