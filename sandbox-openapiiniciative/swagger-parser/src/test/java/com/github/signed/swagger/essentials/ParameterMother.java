package com.github.signed.swagger.essentials;

public class ParameterMother {

    public static String anyParameterName(){
        return "any-parameter-name";
    }

    public static ParameterBuilder anyParameterReferencingAParameterDefinition(String modelDefinition){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.withName(anyParameterName());
        parameterBuilder.referencingParameterDefinition(modelDefinition);
        return parameterBuilder;
    }

    public static ParameterBuilder anyParameterReferencingModelDefinition(String parameterIdentifier) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.withName(anyParameterName());
        parameterBuilder.referencingModelDefinition(parameterIdentifier);
        return parameterBuilder;
    }

    public static ParameterBuilder anyParameter() {
        return new ParameterBuilder().withName(anyParameterName()).inPath().ofTypeString();
    }

    public static String referencedParameterIdentifier() {
        return "referenced-parameter";
    }
}
