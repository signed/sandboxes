package features;

import static features.PropertyMother.propertyReferencingModdelDefinitionModelDefinition;

import com.github.signed.swagger.ResponseBuilder;
import com.github.signed.swagger.StringPropertyBuilder;

public class ResponseMother {
    public static String referencedResponseIdentifier() {
        return "referenced-response-definition";
    }

    public static String anyHttpStatusCode() {
        return "200";
    }

    public static ResponseBuilder anyResponseReferencingModelElement(String modelDefinitionIdentifier) {
        return new ResponseBuilder().withSchema(propertyReferencingModdelDefinitionModelDefinition(modelDefinitionIdentifier)).withDescription("any response description");
    }

    public static ResponseBuilder anyResponseReferencingResponseDefinition(String responseDefinitionIdentifier) {
        return new ResponseBuilder().referencingResponseDefinition(responseDefinitionIdentifier);
    }

    public static ResponseBuilder anyResponseDefinition() {
        return new ResponseBuilder().withSchema(new StringPropertyBuilder());
    }
}
