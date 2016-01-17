package features;

import static com.github.signed.swagger.SwaggerMatcher.hasPathDefinitionsFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;

import com.github.signed.swagger.SwaggerReduce;
import com.github.signed.swagger.SwaggerBuilder;
import com.github.signed.swagger.SwaggerMother;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Swagger;

public class SwaggerReduceSteps {

    private final SwaggerReduce reduce = SwaggerReduce.publicAsMarkerTag();
    private SwaggerBuilder mergedSwaggerDescription;
    private Swagger reducedSwagger;

    @Given("^a merged swagger api description$")
    public void a_merged_swagger_api_description() throws Throwable {
        mergedSwaggerDescription = SwaggerMother.mergedSwaggerDescription();
    }

    @Given("^there is a path definition without the tag$")
    public void there_is_a_path_definition_without_the_tag() throws Throwable {
        mergedSwaggerDescription.withPath("/nottagged").withOption();
    }

    @Given("^there is a path definition with the tag$")
    public void there_is_a_path_definition_with_the_tag() throws Throwable {
        mergedSwaggerDescription.withPath("/tagged").withPost().withTag("public");
    }

    @When("^the swagger api description gets reduced$")
    public void the_swagger_api_description_gets_reduced() throws Throwable {
        reducedSwagger = reduce.reduce(mergedSwaggerDescription.build());
    }

    @Then("^the untagged path definition is removed$")
    public void the_untagged_path_definition_is_removed() throws Throwable {
        assertThat(reducedSwagger, not(hasPathDefinitionsFor("/nottagged")));
    }

    @Then("^the tagged path definition is still present$")
    public void the_tagged_path_definition_is_still_present() throws Throwable {
        assertThat(reducedSwagger, hasPathDefinitionsFor("/tagged"));
    }

    @Then("^the tag is removed$")
    public void the_tag_is_removed() throws Throwable {
        assertThat(reducedSwagger.getPath("/tagged").getPost().getTags(), not(contains("public")));
    }

}
