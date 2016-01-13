package com.github.signed.swagger;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.models.Swagger;

public class SwaggerBuilder {

    private Map<String, PathBuilder> paths = Maps.newLinkedHashMap();
    private Map<String, ModelBuilder> definitions = Maps.newLinkedHashMap();
    private List<TagDefinitionBuilder> tags = Lists.newArrayList();

    public PathBuilder withPath(String path) {
        PathBuilder pathBuilder = new PathBuilder();
        paths.put(path, pathBuilder);
        return pathBuilder;
    }

    public TagDefinitionBuilder defineTag(String tagName) {
        TagDefinitionBuilder tagDefinitionBuilder = new TagDefinitionBuilder();
        tags.add(tagDefinitionBuilder);
        return tagDefinitionBuilder.withName(tagName);
    }

    public ModelBuilder withModelDefinition(String identifier) {
        ModelBuilder modelBuilder = new ModelBuilder();
        definitions.put(identifier, modelBuilder);
        return modelBuilder;
    }

    public Swagger build() {
        Swagger swagger = new Swagger();
        paths.forEach((s, pathBuilder) -> swagger.path(s, pathBuilder.build()));
        definitions.forEach((s, modelBuilder)-> swagger.addDefinition(s, modelBuilder.build()));
        tags.forEach(tagDefinitionBuilder -> swagger.addTag(tagDefinitionBuilder.build()));
        return swagger;
    }
}
