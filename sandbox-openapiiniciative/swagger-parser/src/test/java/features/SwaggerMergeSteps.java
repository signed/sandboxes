package features;


import static org.hamcrest.MatcherAssert.assertThat;

import java.util.LinkedHashMap;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class SwaggerMergeSteps {

    private SwaggerBuilder first;
    private SwaggerBuilder second;
    private Swagger mergeAcpiDefinition;

    @Given("^two distinct swagger api descriptions$")
    public void two_distinct_swagger_api_descriptions() throws Throwable {
        first = SwaggerMother.emptyApiDefinition();
        first.withPath("/first");
        second = SwaggerMother.emptyApiDefinition();
        second.withPath("/second");
    }

    @When("^the two are merged$")
    public void the_two_are_merged() throws Throwable {
        mergeAcpiDefinition = merge(first.build(), second.build());
    }

    @Then("^the path elements of booth are in the resulting swagger api description$")
    public void the_path_elements_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergeAcpiDefinition, SwaggerMatcher.hasPathDefinitionsFor("/first","/second"));
    }

    private Swagger merge(Swagger one, Swagger two) {
        LinkedHashMap<String, Path> mergedPaths = new LinkedHashMap<>();
        mergedPaths.putAll(one.getPaths());
        mergedPaths.putAll(two.getPaths());
        Swagger swagger = new Swagger();
        swagger.setPaths(mergedPaths);
        return swagger;
    }
}
