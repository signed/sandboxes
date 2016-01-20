package features;

import static features.PropertyMother.propertyReferencingModdelDefinitionModelDefinition;

import com.github.signed.swagger.ResponseBuilder;

public class ResponseMother {
    public static String anyHttpStatusCode() {
        return "200";
    }

    public static ResponseBuilder anyResponseBuilderReferencingModelElement(String modelDefinitionIdentifier) {
        return new ResponseBuilder().withSchema(propertyReferencingModdelDefinitionModelDefinition(modelDefinitionIdentifier)).withDescription("any response description");
    }
}
