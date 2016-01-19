package features;

import com.github.signed.swagger.ParameterBuilder;

public class ParameterMother {

    public static String anyParameterName(){
        return "any-parameter-name";
    }

    public static ParameterBuilder anyParameterReferencingParameterDefinition(String modelDefinition){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.withName(anyParameterName());
        parameterBuilder.referencingParameterDefinition(modelDefinition);
        return parameterBuilder;
    }

    public static ParameterBuilder anyParameterReferencingModelDefinition(String s) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.withReferenceToModelDefinition(s);
        return parameterBuilder;
    }
}
