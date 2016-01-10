package features;

import static features.SwaggerMatcher.hasPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;

import java.util.Map;
import java.util.stream.Collectors;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class MergedSwaggerCleanupSteps {

    private SwaggerBuilder mergedSwaggerDescription;
    private Swagger cleanedUp;

    @Given("^a merged swagger api description$")
    public void a_merged_swagger_api_description() throws Throwable {
        mergedSwaggerDescription = SwaggerMother.mergedSwaggerDescription();
    }

    @Given("^there is a path definition without the tag$")
    public void there_is_a_path_definition_without_the_tag() throws Throwable {
        mergedSwaggerDescription.withPath("/nottagged");
    }

    @Given("^there is a path definition with the tag$")
    public void there_is_a_path_definition_with_the_tag() throws Throwable {
        mergedSwaggerDescription.withPath("/tagged").withTag("public");
    }

    @When("^the swagger api description gets cleaned$")
    public void the_swagger_api_description_gets_cleaned() throws Throwable {
        cleanedUp = cleanup(mergedSwaggerDescription.build());
    }

    @Then("^the untagged path definition is removed$")
    public void the_untagged_path_definition_is_removed() throws Throwable {
        assertThat(cleanedUp, not(hasPath("/nottagged")));
    }

    @Then("^the tagged path definition is still present$")
    public void the_tagged_path_definition_is_still_present() throws Throwable {
        assertThat(cleanedUp, hasPath("/tagged"));
    }

    @Then("^the tag is removed$")
    public void the_tag_is_removed() throws Throwable {
        assertThat(cleanedUp.getPath("/tagged").getOptions().getTags(), not(contains("public")));
    }

    private Swagger cleanup(Swagger merged) {
        String markerTag = "public";

        Map<String, Path> aPublic = merged.getPaths().entrySet().stream()
                .filter(stringPathEntry -> stringPathEntry.getValue().getOptions().getTags().contains(markerTag))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Path path : aPublic.values()) {
            path.getOptions().getTags().remove(markerTag);
        }
        Swagger swagger = new Swagger();
        swagger.setPaths(aPublic);
        return swagger;
    }

}
