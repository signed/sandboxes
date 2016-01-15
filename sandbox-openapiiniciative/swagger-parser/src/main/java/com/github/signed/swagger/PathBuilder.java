package com.github.signed.swagger;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class PathBuilder {

    private final List<Consumer<Path>> operations = newArrayList();
    private final List<ParameterBuilder> parameters = newArrayList();

    public OperationBuilder withOption() {
        OperationBuilder builder = new OperationBuilder();
        operations.add(path -> path.set("options", builder.build()));
        return builder;
    }

    public OperationBuilder withPost() {
        OperationBuilder builder = new OperationBuilder();
        operations.add(path -> path.set("post", builder.build()));
        return builder;
    }

    public ParameterBuilder withParameterForAllOperations() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameters.add(parameterBuilder);
        return parameterBuilder;
    }

    public static class ParameterBuilder{
        private Optional<String> maybeReferenceToADefinition = Optional.empty();


        public ParameterBuilder withReferenceToSchemaDefinition(String definitionId){
            maybeReferenceToADefinition = Optional.of(definitionId);
            return this;
        }

        public Parameter build(){
            BodyParameter bodyParameter = new BodyParameter();
            maybeReferenceToADefinition.ifPresent(id -> bodyParameter.setSchema(new RefModel(id)));
            return bodyParameter;
        }
    }

    public Path build() {
        Path path = new Path();
        operations.stream().forEach(operation -> operation.accept(path));
        parameters.stream().forEach(parameterBuilder -> path.addParameter(parameterBuilder.build()));
        return path;
    }
}
