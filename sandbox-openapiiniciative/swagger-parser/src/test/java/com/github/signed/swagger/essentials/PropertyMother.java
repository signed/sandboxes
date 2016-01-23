package com.github.signed.swagger.essentials;

public class PropertyMother {
    public static RefPropertyBuilder propertyReferencingModdelDefinitionModelDefinition(String modelDefinitionIdentifier){
        return new RefPropertyBuilder().withReferenceTo(modelDefinitionIdentifier);
    }
}
