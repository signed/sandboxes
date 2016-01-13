package features;


import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;

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
    private Swagger mergeApiDefinition;

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

    @When("^the two are merged$")
    public void the_two_are_merged() throws Throwable {
        mergeApiDefinition = merger.merge(first.build(), second.build());
    }

    @Then("^the path elements of booth are in the resulting swagger api description$")
    public void the_path_elements_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergeApiDefinition, SwaggerMatcher.hasPathDefinitionsFor("/first","/second"));
    }

    @Then("^the model definitions of booth are in the resulting swagger api description$")
    public void the_model_definitions_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergeApiDefinition.getDefinitions().keySet(), Matchers.contains("something", "anotherthing") );
    }
}
