package features;


import com.github.signed.swagger.Merger;
import com.github.signed.swagger.SwaggerBuilder;
import com.github.signed.swagger.SwaggerMatcher;
import com.github.signed.swagger.SwaggerMother;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Swagger;
import io.swagger.util.Json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SwaggerMergeSteps {

    private final Merger merger = new Merger();
    private SwaggerBuilder first = SwaggerMother.emptyApiDefinition();
    private SwaggerBuilder second = SwaggerMother.emptyApiDefinition();
    private Swagger mergedApiDefinition;
    private RuntimeException mergeException;

    @Given("^two distinct swagger api descriptions$")
    public void two_distinct_swagger_api_descriptions() throws Throwable {
        first.withPath("/first").withOption();
        second.withPath("/second").withOption();
    }

    @Given("^two swagger api description with distinct model definitions$")
    public void two_swagger_api_description_with_distinct_model_definitions() throws Throwable {
        first.withModelDefinition("something").withTypeObject();
        second.withModelDefinition("anotherthing").withTypeObject();
    }

    @Given("^two swagger api descriptions that contain two identical definitions$")
    public void two_swagger_api_descriptions_that_contain_two_identical_definitions() throws Throwable {
        first.withModelDefinition("identical identifier");
        second.withModelDefinition("identical identifier");
    }

    @Given("^two swagger api descriptions that have conflicting definitions$")
    public void two_swagger_api_descriptions_that_have_conflicting_definitions() throws Throwable {
        first.withModelDefinition("identical identifier").withTypeObject();
        second.withModelDefinition("identical identifier").withTypeString();
    }

    @Given("^two swagger api definitions with two identical path definitions$")
    public void two_swagger_api_definitions_with_two_identical_path_definitions() throws Throwable {
        first.withPath("/identical").withPost();
        second.withPath("/identical").withPost();
    }

    @When("^the two are merged$")
    public void the_two_are_merged() throws Throwable {
        try {
            Swagger first = this.first.build();
            Swagger second = this.second.build();
            System.out.println(Json.pretty(first));
            System.out.println(Json.pretty(second));
            mergedApiDefinition = merger.merge(first, second);
            System.out.println(Json.pretty(mergedApiDefinition));
        }catch (RuntimeException ex){
            mergeException = ex;
        }
    }

    @Then("^the path elements of booth are in the resulting swagger api description$")
    public void the_path_elements_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergedApiDefinition, SwaggerMatcher.hasPathDefinitionsFor("/first","/second"));
    }

    @Then("^the path definition is contained only once$")
    public void the_path_definition_is_contained_only_once() throws Throwable {
        assertThat(mergedApiDefinition.getPaths().keySet(), hasSize(1));
    }

    @Then("^the model definitions of booth are in the resulting swagger api description$")
    public void the_model_definitions_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergedApiDefinition, SwaggerMatcher.hasDefinitionsFor("something", "anotherthing") );
    }

    @Then("^the definition is contained only once$")
    public void the_definition_is_contained_only_once() throws Throwable {
        assertThat(mergedApiDefinition.getDefinitions().values(), hasSize(1));
    }

    @Then("^the caller is informed about the conflict$")
    public void the_caller_is_informed_about_the_conflict() throws Throwable {
        assertThat("The caller should have been notified",mergeException, not(nullValue()));
    }
}
