package features;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import com.github.signed.swagger.Merger;
import com.github.signed.swagger.SwaggerBuilder;
import com.github.signed.swagger.SwaggerMatcher;
import com.github.signed.swagger.SwaggerMother;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Swagger;

public class SwaggerMergeSteps {

    private final Merger merger = new Merger();
    private SwaggerBuilder first = SwaggerMother.emptyApiDefinition();
    private SwaggerBuilder second = SwaggerMother.emptyApiDefinition();
    private Swagger mergedApiDefinition;

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
        first.withModelDefinition("identical identifier");
    }

    @When("^the two are merged$")
    public void the_two_are_merged() throws Throwable {
        mergedApiDefinition = merger.merge(first.build(), second.build());
    }

    @Then("^the path elements of booth are in the resulting swagger api description$")
    public void the_path_elements_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergedApiDefinition, SwaggerMatcher.hasPathDefinitionsFor("/first","/second"));
    }

    @Then("^the model definitions of booth are in the resulting swagger api description$")
    public void the_model_definitions_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergedApiDefinition, SwaggerMatcher.hasDefinitionsFor("something", "anotherthing") );
    }

    @Then("^the definition is contained only once$")
    public void the_definition_is_contained_only_once() throws Throwable {
        assertThat(mergedApiDefinition.getDefinitions().values(), hasSize(1));
    }

}
