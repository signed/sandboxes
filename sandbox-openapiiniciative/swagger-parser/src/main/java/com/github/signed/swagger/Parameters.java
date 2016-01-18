package com.github.signed.swagger;

import java.util.Collections;
import java.util.List;

import io.swagger.models.Model;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.RefParameter;

public class Parameters {

    private final Models models = new Models();

    public List<DefinitionReference> definitionReferencesIn(Parameter parameter) {

        if (parameter instanceof BodyParameter) {
            BodyParameter bodyParameter = (BodyParameter) parameter;
            Model schema = bodyParameter.getSchema();
            return models.definitionReferencesIn(schema);
        }
        return Collections.emptyList();
    }

    public List<ParameterReference> parameterReferencesIn(Parameter parameter) {
        if (parameter instanceof RefParameter) {
            return Collections.singletonList(new ParameterReference((RefParameter) parameter));
        }

        if (parameter instanceof BodyParameter) {
            return Collections.emptyList();
        }
        throw new RuntimeException("parameter detection still needs some work");
    }
}
