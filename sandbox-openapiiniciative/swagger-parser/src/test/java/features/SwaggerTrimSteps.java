package features;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.signed.swagger.SwaggerBuilder;
import com.github.signed.swagger.SwaggerMother;
import com.github.signed.swagger.TagDefinitionBuilder;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;

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

    @When("^the swagger api description is trimmed$")
    public void the_swagger_api_description_is_trimmed() throws Throwable {
        trimmedSwagger = trim(swagger.build());
    }

    @Then("^the referenced tag definition is still present$")
    public void the_referenced_tag_definition_is_still_present() throws Throwable {
        assertThat(trimmedSwagger.getTags(), contains(TagDefinitionBuilder.tagDefinitionFor("referenced").build()));
    }

    @Then("^the not referenced tag definition is removed$")
    public void the_not_referenced_tag_definition_is_removed() throws Throwable {
        assertThat(trimmedSwagger.getTags(), not(contains(TagDefinitionBuilder.tagDefinitionFor("not referenced anywhere").build())));
    }

    private Swagger trim(Swagger swagger) {
        Set<String> tagReferences = swagger.getPaths().values().stream().map(allTagsReferencedIn()).flatMap(Set::stream).collect(Collectors.toSet());

        List<Tag> referencedTagDefinitions = swagger.getTags().stream().filter(tag -> tagReferences.contains(tag.getName())).collect(Collectors.toList());
        swagger.setTags(referencedTagDefinitions);
        return swagger;
    }

    private Function<Path, Set<String>> allTagsReferencedIn() {
        return path -> path.getOperations().stream().map(Operation::getTags).flatMap(List::stream).collect(Collectors.toSet());
    }
}
