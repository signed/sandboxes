package com.github.signed.swagger.trim;

import io.swagger.models.parameters.RefParameter;

public class ParameterReference {

    private final RefParameter refParameter;

    public ParameterReference(RefParameter refParameter) {
        this.refParameter = refParameter;
    }

    public String parameterIdentifier() {
        return refParameter.getSimpleRef();
    }

}
