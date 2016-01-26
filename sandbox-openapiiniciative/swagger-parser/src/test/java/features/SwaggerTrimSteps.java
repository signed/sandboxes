package features;

import static com.github.signed.swagger.essentials.ParameterMother.anyParameter;
import static com.github.signed.swagger.essentials.ParameterMother.anyParameterName;
import static com.github.signed.swagger.essentials.ParameterMother.anyParameterReferencingAParameterDefinition;
import static com.github.signed.swagger.essentials.ParameterMother.anyParameterReferencingModelDefinition;
import static com.github.signed.swagger.essentials.ParameterMother.referencedParameterIdentifier;
import static com.github.signed.swagger.essentials.PathMother.anyPath;
import static com.github.signed.swagger.essentials.ResponseMother.anyHttpStatusCode;
import static com.github.signed.swagger.essentials.ResponseMother.anyResponseDefinition;
import static com.github.signed.swagger.essentials.ResponseMother.anyResponseReferencingModelElement;
import static com.github.signed.swagger.essentials.ResponseMother.anyResponseReferencingResponseDefinition;
import static com.github.signed.swagger.essentials.ResponseMother.referencedResponseIdentifier;
import static com.github.signed.swagger.essentials.SwaggerMatcher.hasModelDefinitionsFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.Matchers;

import com.github.signed.swagger.essentials.ResponseMother;
import com.github.signed.swagger.essentials.SwaggerBuilder;
import com.github.signed.swagger.essentials.SwaggerMother;
import com.github.signed.swagger.essentials.TagDefinitionBuilder;
import com.github.signed.swagger.trim.SwaggerTrim;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Swagger;
import io.swagger.util.Yaml;

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
        swagger.withPath(anyPath()).withPost().withTag("referenced");
    }

    @Given("^an unreferenced model definition$")
    public void an_unreferenced_model_definition() throws Throwable {
        swagger.withModelDefinition("some-id").withTypeObject();
    }

    @Given("^a model definition that is referenced in a path$")
    public void a_model_definition_that_is_referenced_in_a_path() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT).withTypeString();
        swagger.withPath("/some/path").withParameterForAllOperations().referencingModelDefinition(REFERENCED_MODEL_ELEMENT);
    }

    @Given("^a model definition that is only referenced by another model definition that is not referenced anywhere$")
    public void a_model_definition_that_is_only_referenced_by_another_model_definition_that_is_not_referenced_anywhere() throws Throwable {
        swagger.withModelDefinition("only-referenced-in-to-be-removed-definition").withTypeObject();
        swagger.withModelDefinition("unreferenced").withTypeObject().withReferencePropertyNamed("something").withReferenceTo("only-referenced-in-to-be-removed-definition");
    }

    @Given("^a model definition that is only referenced in a parameter definition$")
    public void a_model_definition_that_is_only_referenced_in_a_parameter_definition() throws Throwable {
        swagger.withParameterDefinition(referencedParameterIdentifier(), anyParameterReferencingModelDefinition(REFERENCED_MODEL_ELEMENT));
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT).withTypeString();
    }

    @Given("^the parameter definition is referenced anywhere$")
    public void the_parameter_definition_is_referenced_anywhere() throws Throwable {
        swagger.withPath(anyPath()).withParameterForAllOperations(anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^a parameter definition that is not referenced anywhere$")
    public void a_swagger_api_description_with_a_parameter_definition_that_is_not_referenced_anywhere() throws Throwable {
        swagger.withParameterDefinition("unreferenced-parameter", anyParameter());
    }

    @Given("^a parameter definition that is referenced in any path$")
    public void a_parameter_definition_that_is_referenced_in_any_path() throws Throwable {
        swagger.withParameterDefinition(referencedParameterIdentifier(), anyParameter());
        swagger.withPath(anyPath()).withParameterForAllOperations(anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^a parameter definition that is referenced in any operation$")
    public void a_parameter_definition_that_is_referenced_in_any_operation() throws Throwable {
        swagger.withParameterDefinition(referencedParameterIdentifier(), anyParameter());
        swagger.withPath(anyPath()).withOption().withParameter(anyParameterName(), anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^a model definition referenced by a parameter$")
    public void a_model_definition_referenced_by_a_parameter() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withPath(anyPath()).withOption().withParameter(anyParameterName(), anyParameterReferencingModelDefinition(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^a model definition that is referenced in a response$")
    public void a_model_definition_that_is_referenced_in_a_response() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withPath(anyPath()).withOption().withResponse(anyHttpStatusCode(), anyResponseReferencingModelElement(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^a model definition that is referenced in a response definition$")
    public void a_model_definition_that_is_referenced_in_response_definition() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withResponseDefinition(referencedResponseIdentifier(), ResponseMother.anyResponseReferencingModelElement(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^the response definition is referenced anywhere$")
    public void the_response_definition_is_referenced_anywhere() throws Throwable {
        swagger.withPath(anyPath()).withOption().withResponse(anyHttpStatusCode(), anyResponseReferencingResponseDefinition(referencedResponseIdentifier()));
    }

    @Given("^a response definition that is not referenced anywhere$")
    public void a_response_definition_that_is_not_referenced_anywhere() throws Throwable {
        swagger.withResponseDefinition("not-referenced-response", anyResponseDefinition());
    }

    @Given("^a response definition that is referenced in any operation$")
    public void a_response_definition_that_is_not_referenced_in_any_operation() throws Throwable {
        swagger.withResponseDefinition(referencedResponseIdentifier(), anyResponseDefinition());
        swagger.withPath(anyPath()).withPost().withResponse(ResponseMother.anyHttpStatusCode(), anyResponseReferencingResponseDefinition(referencedResponseIdentifier()));
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

    @Then("^the model definition is removed$")
    public void the_model_definition_is_removed() throws Throwable {
        assertThat(trimmedSwagger.getDefinitions(), Matchers.nullValue());
    }

    @Then("^the model definition is still present$")
    public void the_model_definition_is_still_present() throws Throwable {
        assertThat(trimmedSwagger, hasModelDefinitionsFor(REFERENCED_MODEL_ELEMENT));
    }

    @Then("^booth definitions are removed$")
    public void booth_definitions_are_removed() throws Throwable {
        assertThat(trimmedSwagger, not(hasModelDefinitionsFor("only-referenced-in-to-be-removed-definition")));
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
