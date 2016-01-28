package com.github.signed.swagger.essentials;

public class ParameterMother {

    public static String anyParameterName(){
        return "any-parameter-name";
    }

    public static String referencedParameterIdentifier() {
        return "referenced-parameter";
    }

    public static String anyParameterIdentifier() {
        return "any-parameter-identifier";
    }

    public static ParameterBuilder anyParameterReferencingParameterDefinition(String parameterIdentifier){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.withName(anyParameterName());
        parameterBuilder.referencingParameterDefinition(parameterIdentifier);
        return parameterBuilder;
    }

    public static ParameterBuilder anyParameterReferencingModelDefinition(String modelIdentifier) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.withName(anyParameterName());
        parameterBuilder.referencingModelDefinition(modelIdentifier);
        return parameterBuilder;
    }

    public static ParameterBuilder anyParameterThatCanOccurMultipleTimesInASingleOperation() {
        return pathParameter();
    }

    public static ParameterBuilder anyParameter() {
        return pathParameter();
    }

    private static ParameterBuilder pathParameter() {
        return new ParameterBuilder().withName(anyParameterName()).inPath().ofTypeString();
    }
}
