package features;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.github.signed.swagger.SwaggerBuilder;
import com.github.signed.swagger.SwaggerMother;
import com.github.signed.swagger.SwaggerTrim;
import com.github.signed.swagger.TagDefinitionBuilder;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Swagger;

public class SwaggerTrimSteps {

    private SwaggerBuilder swagger;
    private Swagger trimmedSwagger;

    @Given("^a swagger api description with a tag definition that is not referenced in an operation$")
    public void a_swagger_api_description_with_a_tag_definition_that_is_not_referenced_in_an_operation() throws Throwable {
        swagger = SwaggerMother.emptyApiDefinition();
        swagger.defineTag("not referenced anywhere");
    }

    @Given("^a tag definition that is referenced in a path operation$")
    public void a_tag_definition_that_is_referenced_in_a_path_operation() throws Throwable {
        swagger.defineTag("referenced");
        swagger.withPath("/any").withPost().withTag("referenced");
    }

    @Given("^a swagger api description with only unreferenced tag definitions$")
    public void a_swagger_api_description_with_only_unreferenced_tag_definitions() throws Throwable {
        a_swagger_api_description_with_a_tag_definition_that_is_not_referenced_in_an_operation();
    }

    @When("^the swagger api description is trimmed$")
    public void the_swagger_api_description_is_trimmed() throws Throwable {
        trimmedSwagger = new SwaggerTrim().trim(swagger.build());
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

}
