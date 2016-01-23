package com.github.signed.swagger.essentials;

import java.util.Collections;
import java.util.List;

import com.github.signed.swagger.trim.DefinitionReference;
import com.github.signed.swagger.trim.ParameterReference;

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
        return Collections.emptyList();
    }
}
