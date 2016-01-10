package com.github.signed.swagger;

import java.util.Map;

import com.google.common.collect.Maps;

import io.swagger.models.Swagger;

public class SwaggerBuilder {

    private Map<String, PathBuilder> paths = Maps.newLinkedHashMap();

    public PathBuilder withPath(String path){
        PathBuilder pathBuilder = new PathBuilder();
        paths.put(path, pathBuilder);
        return pathBuilder;
    }

    public Swagger build() {
        Swagger swagger = new Swagger();
        paths.forEach((s, pathBuilder) -> swagger.path(s, pathBuilder.build()));
        return swagger;
    }
}
