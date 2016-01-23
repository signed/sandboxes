package features;

import com.github.signed.swagger.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Swagger;
import io.swagger.util.Yaml;
import org.hamcrest.Matchers;

import static com.github.signed.swagger.ParameterMother.*;
import static com.github.signed.swagger.ResponseMother.*;
import static com.github.signed.swagger.SwaggerMatcher.hasDefinitionsFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SwaggerTrimSteps {

    public static final String REFERENCED_MODEL_ELEMENT = "referenced-model-element";
    private final SwaggerBuilder swagger = SwaggerMother.emptyApiDefinition();
    private Swagger trimmedSwagger;

    @Given("^a tag definition that is not referenced in an operation$")
    public void a_tag_definition_that_is_not_referenced_in_an_operation() throws Throwable {
        swagger.defineTag("not referenced anywhere");
    }

    @Given("^only unreferenced tag definitions$")
    public void only_unreferenced_tag_definitions() throws Throwable {
        a_tag_definition_that_is_not_referenced_in_an_operation();
    }

    @Given("^a tag definition that is referenced in a path operation$")
    public void a_tag_definition_that_is_referenced_in_a_path_operation() throws Throwable {
        swagger.defineTag("referenced");
        swagger.withPath(PathMother.anyPath()).withPost().withTag("referenced");
    }

    @Given("^an unreferenced definition$")
    public void an_unreferenced_definition() throws Throwable {
        swagger.withModelDefinition("some-id").withTypeObject();
    }

    @Given("^a model definition that is referenced in a path$")
    public void a_model_definition_that_is_referenced_in_a_path() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT).withTypeString();
        swagger.withPath("/some/path").withParameterForAllOperations().withReferenceToAModelDefinition(REFERENCED_MODEL_ELEMENT);
    }

    @Given("^a definition$")
    public void a_definition() throws Throwable {
        swagger.withModelDefinition("only-referenced-in-to-be-removed-definition").withTypeObject();
    }

    @Given("^only a parameter definition references a model definition$")
    public void only_a_parameter_definition_references_a_model_definition() throws Throwable {
        swagger.withParameterDefinition(referencedParameterIdentifier()).withReferenceToAModelDefinition(REFERENCED_MODEL_ELEMENT).withName(anyParameterName());
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT).withTypeString();
    }

    @Given("^the parameter definition is referenced anywhere$")
    public void the_parameter_definition_is_referenced_anywhere() throws Throwable {
        swagger.withPath(PathMother.anyPath()).withParameterForAllOperations(anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^this definition is only referenced by another unreferenced definition$")
    public void this_definition_is_only_referenced_by_another_unreferenced_definition() throws Throwable {
        swagger.withModelDefinition("unreferenced").withTypeObject().withReferencePropertyNamed("something").withReferenceTo("only-referenced-in-to-be-removed-definition");
    }

    @Given("^a parameter definition that is not referenced anywhere$")
    public void a_swagger_api_description_with_a_parameter_definition_that_is_not_referenced_anywhere() throws Throwable {
        swagger.withParameterDefinition("unreferenced-parameter");
    }

    @Given("^a parameter definition$")
    public void a_swagger_api_description_with_a_parameter_definition() throws Throwable {
        swagger.withParameterDefinition(referencedParameterIdentifier());
    }

    @Given("^the parameter definition is referenced in any operation$")
    public void the_parameter_definition_is_referenced_in_any_operation() throws Throwable {
        swagger.withPath(PathMother.anyPath()).withOption().withParameter(anyParameterName(), anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^the parameter definition is referenced in any path$")
    public void the_parameter_definition_is_referenced_in_any_path() throws Throwable {
        swagger.withPath(PathMother.anyPath()).withParameterForAllOperations(anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^a parameter references a model definition$")
    public void a_swagger_api_description_where_a_parameter_references_a_model_definition() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withPath(PathMother.anyPath()).withOption().withParameter(anyParameterName(), anyParameterReferencingModelDefinition(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^a response references a model definition$")
    public void a_swagger_api_description_where_a_response_references_a_model_definition() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withPath(PathMother.anyPath()).withOption().withResponse(anyHttpStatusCode(), anyResponseReferencingModelElement(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^a response definition references a model definition$")
    public void a_swagger_api_description_where_a_response_definition_references_a_model_definition() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withResponseDefinition(referencedResponseIdentifier(), ResponseMother.anyResponseReferencingModelElement(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^the response definition is referenced anywhere$")
    public void the_response_definition_is_referenced_anywhere() throws Throwable {
        swagger.withPath(PathMother.anyPath()).withOption().withResponse(anyHttpStatusCode(), anyResponseReferencingResponseDefinition(referencedResponseIdentifier()));
    }

    @Given("^a response definition that is not referenced anywhere$")
    public void a_swagger_api_description_with_a_response_definition_that_is_not_referenced_anywhere() throws Throwable {
        swagger.withResponseDefinition("not-referenced-response", anyResponseDefinition());
    }

    @Given("^a response definition$")
    public void a_swagger_api_description_with_a_response_definition() throws Throwable {
        swagger.withResponseDefinition(referencedResponseIdentifier(), anyResponseDefinition());
    }

    @Given("^the response definition is referenced in any operation$")
    public void the_response_definition_is_referenced_in_any_operation() throws Throwable {
        swagger.withPath(PathMother.anyPath()).withPost().withResponse(ResponseMother.anyHttpStatusCode(), anyResponseReferencingResponseDefinition(referencedResponseIdentifier()));
    }

    @When("^trimmed$")
    public void trimmed() throws Throwable {
        Swagger build = swagger.build();
        Yaml.prettyPrint(build);
        trimmedSwagger = new SwaggerTrim().trim(build);
        Yaml.prettyPrint(trimmedSwagger);
    }

    @Then("^the referenced tag definition is still present$")
    public void the_referenced_tag_definition_is_still_present() throws Throwable {
        assertThat(trimmedSwagger.getTags(), contains(TagDefinitionBuilder.tagDefinitionFor("referenced").build()));
    }

    @Then("^the not referenced tag definition is removed$")
    public void the_not_referenced_tag_definition_is_removed() throws Throwable {
        assertThat(trimmedSwagger.getTags(), not(contains(TagDefinitionBuilder.tagDefinitionFor("not referenced anywhere").build())));
    }

    @Then("^there is no tag property in the resulting json$")
    public void there_is_no_tag_property_in_the_resulting_json() throws Throwable {
        assertThat(trimmedSwagger.getTags(), nullValue());
    }

    @Then("^the unreferenced definition is removed$")
    public void the_unreferenced_definition_is_removed() throws Throwable {
        assertThat(trimmedSwagger.getDefinitions(), Matchers.nullValue());
    }

    @Then("^the referenced model definition is still present$")
    public void the_referenced_model_definition_is_still_present() throws Throwable {
        assertThat(trimmedSwagger, hasDefinitionsFor(REFERENCED_MODEL_ELEMENT));
    }

    @Then("^booth definitions are removed$")
    public void booth_definitions_are_removed() throws Throwable {
        assertThat(trimmedSwagger, not(hasDefinitionsFor("only-referenced-in-to-be-removed-definition")));
    }

    @Then("^the unreferenced parameter definition is removed$")
    public void the_unreferenced_parameter_definition_is_removed() throws Throwable {
        assertThat(trimmedSwagger.getParameters(), nullValue());
    }

    @Then("^the referenced parameter definition is still present$")
    public void the_referenced_parameter_definition_is_still_present() throws Throwable {
        assertThat(trimmedSwagger.getParameters(), hasKey(referencedParameterIdentifier()));
    }


    @Then("^the unreferenced response definition is removed$")
    public void the_unreferenced_response_definition_is_removed() throws Throwable {
        assertThat(trimmedSwagger.getResponses(), nullValue());
    }

    @Then("^the referenced response definition is still present$")
    public void the_referenced_response_definition_is_still_present() throws Throwable {
        assertThat(trimmedSwagger.getResponses(), hasKey(referencedResponseIdentifier()));
    }

}
