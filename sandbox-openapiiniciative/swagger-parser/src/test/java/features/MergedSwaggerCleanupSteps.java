package features;

import static com.github.signed.swagger.SwaggerMatcher.hasPathDefinitionsFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;

import com.github.signed.swagger.CleanUp;
import com.github.signed.swagger.SwaggerBuilder;
import com.github.signed.swagger.SwaggerMother;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Swagger;

public class MergedSwaggerCleanupSteps {

    private final CleanUp cleanUp = new CleanUp();
    private SwaggerBuilder mergedSwaggerDescription;
    private Swagger cleanedUp;

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

    @When("^the swagger api description gets cleaned$")
    public void the_swagger_api_description_gets_cleaned() throws Throwable {
        cleanedUp = cleanUp.cleanup(mergedSwaggerDescription.build());
    }

    @Then("^the untagged path definition is removed$")
    public void the_untagged_path_definition_is_removed() throws Throwable {
        assertThat(cleanedUp, not(hasPathDefinitionsFor("/nottagged")));
    }

    @Then("^the tagged path definition is still present$")
    public void the_tagged_path_definition_is_still_present() throws Throwable {
        assertThat(cleanedUp, hasPathDefinitionsFor("/tagged"));
    }

    @Then("^the tag is removed$")
    public void the_tag_is_removed() throws Throwable {
        assertThat(cleanedUp.getPath("/tagged").getPost().getTags(), not(contains("public")));
    }

}