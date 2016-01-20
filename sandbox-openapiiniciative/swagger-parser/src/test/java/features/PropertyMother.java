package features;

import com.github.signed.swagger.RefPropertyBuilder;

public class PropertyMother {
    public static RefPropertyBuilder propertyReferencingModdelDefinitionModelDefinition(String modelDefinitionIdentifier){
        return new RefPropertyBuilder().withReferenceTo(modelDefinitionIdentifier);
    }
}
