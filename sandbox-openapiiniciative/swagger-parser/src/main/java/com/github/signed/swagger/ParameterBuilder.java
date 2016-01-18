package com.github.signed.swagger;

import java.util.Optional;

import io.swagger.models.RefModel;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class ParameterBuilder {
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
