package com.github.signed.swagger;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.models.Swagger;

public class SwaggerBuilder {

    private final Map<String, PathBuilder> paths = Maps.newLinkedHashMap();
    private final Map<String, ModelBuilder> definitions = Maps.newLinkedHashMap();
    private final Map<String, ParameterBuilder> parameters = Maps.newLinkedHashMap();
    private final InfoBuilder infoBuilder = new InfoBuilder();
    private List<TagDefinitionBuilder> tags = Lists.newArrayList();

    public InfoBuilder withInfo() {
        return infoBuilder;
    }

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

    public ModelBuilder withModelDefinition(String modelIdentifier) {
        ModelBuilder modelBuilder = new ModelBuilder();
        definitions.put(modelIdentifier, modelBuilder);
        return modelBuilder;
    }

    public ParameterBuilder withParameterDefinition(String parameterIdentifier) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameters.put(parameterIdentifier, parameterBuilder);
        return parameterBuilder;
    }

    public Swagger build() {
        Swagger swagger = new Swagger();
        swagger.setInfo(infoBuilder.build());
        paths.forEach((s, pathBuilder) -> swagger.path(s, pathBuilder.build()));
        definitions.forEach((s, modelBuilder) -> swagger.addDefinition(s, modelBuilder.build()));
        parameters.forEach((s, parameterBuilder) -> swagger.addParameter(s, parameterBuilder.build()));
        tags.forEach(tagDefinitionBuilder -> swagger.addTag(tagDefinitionBuilder.build()));
        return swagger;
    }
}
