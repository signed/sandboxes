package com.github.signed.swagger;

import java.util.Collections;
import java.util.List;

import io.swagger.models.Model;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class Parameters {

    private final Models models = new Models();

    public List<DefinitionReference> definitionReferencesIn(Parameter parameter){

        if (parameter instanceof BodyParameter) {
            BodyParameter bodyParameter = (BodyParameter) parameter;
            Model schema = bodyParameter.getSchema();
            return models.definitionReferencesIn(schema);
        }
        return Collections.emptyList();
    }

}
