package com.github.signed.swagger;

import java.util.Optional;

import io.swagger.models.RefModel;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.RefParameter;

public class ParameterBuilder {
    private Optional<String> maybeReferenceToADefinition = Optional.empty();
    private Optional<String> maybeReferenceToAParameterDefinition = Optional.empty();
    private Optional<String> maybeAName = Optional.empty();

    public ParameterBuilder withReferenceToModelDefinition(String definitionId) {
        maybeReferenceToADefinition = Optional.of(definitionId);
        return this;
    }

    public ParameterBuilder referencingParameterDefinition(String parameterKey) {
        maybeReferenceToAParameterDefinition = Optional.ofNullable(parameterKey);
        return this;
    }

    public ParameterBuilder withName(String name){
        maybeAName = Optional.ofNullable(name);
        return this;
    }

    public Parameter build() {
        if (maybeReferenceToADefinition.isPresent()) {
            BodyParameter bodyParameter = new BodyParameter();
            maybeReferenceToADefinition.ifPresent(id -> bodyParameter.setSchema(new RefModel(id)));
            maybeAName.ifPresent(bodyParameter::setName);
            return bodyParameter;
        }

        if (maybeReferenceToAParameterDefinition.isPresent()) {
            return new RefParameter(maybeReferenceToAParameterDefinition.get());
        }
        return new PathParameter();
    }
}
