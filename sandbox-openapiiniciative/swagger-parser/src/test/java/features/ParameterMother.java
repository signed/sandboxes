package features;

import com.github.signed.swagger.ParameterBuilder;

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

    public static ParameterBuilder anyParameterReferencingModelDefinition(String s) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.withReferenceToAModelDefinition(s);
        return parameterBuilder;
    }

    public static String referencedParameterIdentifier() {
        return "referenced-parameter";
    }
}
