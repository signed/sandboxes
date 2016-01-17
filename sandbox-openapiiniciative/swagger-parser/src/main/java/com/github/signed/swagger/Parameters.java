package com.github.signed.swagger;

import java.util.Collections;
import java.util.List;

import io.swagger.models.Model;
import io.swagger.models.RefModel;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class Parameters {

    public List<DefinitionReference> definitionReferencesIn(Parameter parameter){

        if (parameter instanceof BodyParameter) {
            BodyParameter bodyParameter = (BodyParameter) parameter;
            Model schema = bodyParameter.getSchema();
            if (schema instanceof RefModel) {
                return Collections.singletonList((DefinitionReference) ((RefModel) schema)::getSimpleRef);
            } else {
                throw new RuntimeException("still work to do");
            }
        }
        return Collections.emptyList();
    }
}
