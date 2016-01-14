package com.github.signed.swagger;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;

public class SwaggerTrim {

    public Swagger trim(Swagger swagger) {
        Set<String> tagReferences = ofNullable(swagger.getPaths()).orElse(emptyMap()).values().stream().map(allTagsReferencedInPath()).flatMap(Set::stream).collect(Collectors.toSet());
        List<Tag> referencedTagDefinitions = ofNullable(swagger.getTags()).orElse(emptyList()).stream().filter(tag -> tagReferences.contains(tag.getName())).collect(Collectors.toList());
        swagger.setTags((referencedTagDefinitions.isEmpty()) ? null : referencedTagDefinitions);
        return swagger;
    }

    private Function<Path, Set<String>> allTagsReferencedInPath() {
        return path -> path.getOperations().stream()
                .map(Operation::getTags)
                .map( strings -> (null == strings) ? Collections.<String>emptyList() : strings)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }
}
