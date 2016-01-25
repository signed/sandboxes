package com.github.signed.swagger.sort;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.models.Swagger;
import io.swagger.models.Tag;

public class SwaggerSort {

    public Swagger sort(Swagger swagger){
        swagger.setTags(sortedTags(swagger));
        return swagger;
    }

    private List<Tag> sortedTags(Swagger swagger) {
        return swagger.getTags().stream().sorted((o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase())).collect(Collectors.toList());
    }
}
