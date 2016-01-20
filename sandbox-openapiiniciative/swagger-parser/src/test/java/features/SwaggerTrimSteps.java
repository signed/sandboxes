package features;

import static com.github.signed.swagger.SwaggerMatcher.hasDefinitionsFor;
import static features.ParameterMother.anyParameterName;
import static features.ParameterMother.anyParameterReferencingAParameterDefinition;
import static features.ParameterMother.anyParameterReferencingModelDefinition;
import static features.ParameterMother.referencedParameterIdentifier;
import static features.ResponseMother.anyHttpStatusCode;
import static features.ResponseMother.anyResponseBuilderReferencingModelElement;
import static features.ResponseMother.referencedResponseDefinitionIdentifier;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.Matchers;

import com.github.signed.swagger.SwaggerBuilder;
import com.github.signed.swagger.SwaggerMother;
import com.github.signed.swagger.SwaggerTrim;
import com.github.signed.swagger.TagDefinitionBuilder;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Swagger;
import io.swagger.util.Yaml;

public class SwaggerTrimSteps {

    public static final String REFERENCED_MODEL_ELEMENT = "referenced-model-element";
    private final SwaggerBuilder swagger = SwaggerMother.emptyApiDefinition();
    private Swagger trimmedSwagger;

    @Given("^a swagger api description with a tag definition that is not referenced in an operation$")
    public void a_swagger_api_description_with_a_tag_definition_that_is_not_referenced_in_an_operation() throws Throwable {
        swagger.defineTag("not referenced anywhere");
    }

    @Given("^a swagger api description with only unreferenced tag definitions$")
    public void a_swagger_api_description_with_only_unreferenced_tag_definitions() throws Throwable {
        a_swagger_api_description_with_a_tag_definition_that_is_not_referenced_in_an_operation();
    }

    @Given("^a tag definition that is referenced in a path operation$")
    public void a_tag_definition_that_is_referenced_in_a_path_operation() throws Throwable {
        swagger.defineTag("referenced");
        swagger.withPath("/any").withPost().withTag("referenced");
    }

    @Given("^a swagger api description with an unreferenced definition$")
    public void a_swagger_api_description_with_an_unreferenced_definition() throws Throwable {
        swagger.withModelDefinition("some-id").withTypeObject();
    }

    @Given("^a swagger api description where a path references a model definition$")
    public void a_swagger_api_description_where_a_path_references_a_model_definition() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT).withTypeString();
        swagger.withPath("/some/path").withParameterForAllOperations().withReferenceToAModelDefinition(REFERENCED_MODEL_ELEMENT);
    }

    @Given("^a swagger api description with a definition$")
    public void a_swagger_api_description_with_a_definition() throws Throwable {
        swagger.withModelDefinition("only-referenced-in-to-be-removed-definition").withTypeObject();
    }

    @Given("^a swagger api description where only a parameter definition references a model definition$")
    public void a_swagger_api_description_where_only_a_parameter_definition_references_a_model_definition() throws Throwable {
        swagger.withParameterDefinition(referencedParameterIdentifier()).withReferenceToAModelDefinition(REFERENCED_MODEL_ELEMENT).withName(anyParameterName());
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT).withTypeString();
    }

    @Given("^the parameter definition is referenced anywhere$")
    public void the_parameter_definition_is_referenced_anywhere() throws Throwable {
        swagger.withPath("/any").withParameterForAllOperations(anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^this definition is only referenced by another unreferenced definition$")
    public void this_definition_is_only_referenced_by_another_unreferenced_definition() throws Throwable {
        swagger.withModelDefinition("unreferenced").withTypeObject().withReferencePropertyNamed("something").withReferenceTo("only-referenced-in-to-be-removed-definition");
    }

    @Given("^a swagger api description with a parameter definition that is not referenced anywhere$")
    public void a_swagger_api_description_with_a_parameter_definition_that_is_not_referenced_anywhere() throws Throwable {
        swagger.withParameterDefinition("unreferenced-parameter");
    }

    @Given("^a swagger api description with a parameter definition$")
    public void a_swagger_api_description_with_a_parameter_definition() throws Throwable {
        swagger.withParameterDefinition(referencedParameterIdentifier());
    }

    @Given("^the parameter definition is referenced in any operation$")
    public void the_parameter_definition_is_referenced_in_any_operation() throws Throwable {
        swagger.withPath("/any").withOption().withParameter(anyParameterName(), anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^the parameter definition is referenced in any path$")
    public void the_parameter_definition_is_referenced_in_any_path() throws Throwable {
        swagger.withPath("/any").withParameterForAllOperations(anyParameterReferencingAParameterDefinition(referencedParameterIdentifier()));
    }

    @Given("^a swagger api description where a parameter references a model definition$")
    public void a_swagger_api_description_where_a_parameter_references_a_model_definition() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withPath("/any").withOption().withParameter(anyParameterName(), anyParameterReferencingModelDefinition(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^a swagger api description where a response references a model definition$")
    public void a_swagger_api_description_where_a_response_references_a_model_definition() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withPath("/any").withOption().withResponse(anyHttpStatusCode(), anyResponseBuilderReferencingModelElement(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^a swagger api description where a response definition references a model definition$")
    public void a_swagger_api_description_where_a_response_definition_references_a_model_definition() throws Throwable {
        swagger.withModelDefinition(REFERENCED_MODEL_ELEMENT);
        swagger.withResponseDefinition(referencedResponseDefinitionIdentifier(), ResponseMother.anyResponseBuilderReferencingModelElement(REFERENCED_MODEL_ELEMENT));
    }

    @Given("^the response definition is referenced anywhere$")
    public void the_response_definition_is_referenced_anywhere() throws Throwable {
        swagger.withPath("/any").withOption().withResponse(anyHttpStatusCode(), ResponseMother.anyResponseBuilderReferencingResponseDefinition(referencedResponseDefinitionIdentifier()));
    }

    @When("^the swagger api description is trimmed$")
    public void the_swagger_api_description_is_trimmed() throws Throwable {
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

}
