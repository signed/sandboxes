package features;


import static org.hamcrest.MatcherAssert.assertThat;

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
    private SwaggerBuilder first;
    private SwaggerBuilder second;
    private Swagger mergeAcpiDefinition;

    @Given("^two distinct swagger api descriptions$")
    public void two_distinct_swagger_api_descriptions() throws Throwable {
        first = SwaggerMother.emptyApiDefinition();
        first.withPath("/first").withOption();
        second = SwaggerMother.emptyApiDefinition();
        second.withPath("/second").withOption();
    }

    @When("^the two are merged$")
    public void the_two_are_merged() throws Throwable {
        mergeAcpiDefinition = merger.merge(first.build(), second.build());
    }

    @Then("^the path elements of booth are in the resulting swagger api description$")
    public void the_path_elements_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergeAcpiDefinition, SwaggerMatcher.hasPathDefinitionsFor("/first","/second"));
    }

}
