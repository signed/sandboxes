package features;


import static org.hamcrest.MatcherAssert.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class SwaggerMergeSteps {

    public static class SwaggerMother {
        public static SwaggerBuilder emptyApiDefinition() {
            return new SwaggerBuilder();
        }
    }

    public static class SwaggerBuilder {

        private Optional<String> path = Optional.empty();


        public SwaggerBuilder withPath(String path) {
            this.path = Optional.of(path);
            return this;
        }

        public Swagger build() {
            Swagger swagger = new Swagger();
            path.ifPresent(s -> swagger.path(s, new Path()));
            return swagger;
        }
    }

    private SwaggerBuilder first;
    private SwaggerBuilder second;
    private Swagger mergeAcpiDefinition;

    @Given("^two distinct swagger api descriptions$")
    public void two_distinct_swagger_api_descriptions() throws Throwable {
        first = SwaggerMother.emptyApiDefinition().withPath("/first");
        second = SwaggerMother.emptyApiDefinition().withPath("/second");
    }

    @When("^the two are merged$")
    public void the_two_are_merged() throws Throwable {
        mergeAcpiDefinition = merge(first.build(), second.build());
    }

    @Then("^the path elements of booth are in the resulting swagger api description$")
    public void the_path_elements_of_booth_are_in_the_resulting_swagger_api_description() throws Throwable {
        assertThat(mergeAcpiDefinition, CoreMatchers.allOf(SwaggerMatcher.hasPath("/first"), SwaggerMatcher.hasPath("/second")));
    }

    public static class SwaggerMatcher extends TypeSafeDiagnosingMatcher<Swagger> {

        public static Matcher<Swagger> hasPath(String path) {
            return new SwaggerMatcher();
        }

        private Matcher<Map<String, Path>> pathExistMatcher = AllOf.allOf(IsMapContaining.hasKey("/first"), IsMapContaining.hasKey("/second"));


        @Override
        protected boolean matchesSafely(Swagger item, Description mismatchDescription) {
            boolean requiredPathsExist = pathExistMatcher.matches(item.getPaths());
            if (!requiredPathsExist) {
                pathExistMatcher.describeMismatch(item.getPaths(), mismatchDescription);
            }

            return requiredPathsExist;
        }

        @Override
        public void describeTo(Description description) {
            pathExistMatcher.describeTo(description);
        }
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
