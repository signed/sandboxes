package features;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;

import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class SwaggerMatcher extends TypeSafeDiagnosingMatcher<Swagger> {

    public static Matcher<Swagger> hasPathDefinitionsFor(String ... paths) {
        return new SwaggerMatcher().hasPaths(paths);
    }

    private Matcher<Map<String, Path>> pathExistMatcher = new BaseMatcher<Map<String, Path>>() {
        @Override
        public boolean matches(Object item) {
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("any paths");
        }
    };

    public Matcher<Swagger> hasPaths(String ... paths) {
        pathExistMatcher = AllOf.allOf(asList(paths).stream().map(IsMapContaining::hasKey).collect(toList()));
        return this;
    }

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
