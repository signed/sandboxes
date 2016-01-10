package features;

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

    public static Matcher<Swagger> hasPath(String one, String two) {
        return new SwaggerMatcher().hasPaths(one, two);
    }

    public static Matcher<Swagger> hasPath(String one) {
        return new SwaggerMatcher().hasPaths(one);
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

    public SwaggerMatcher hasPaths(String one, String two){
        pathExistMatcher = AllOf.allOf(IsMapContaining.hasKey(one), IsMapContaining.hasKey(two));
        return this;
    }

    public Matcher<Swagger> hasPaths(String one) {
        pathExistMatcher = AllOf.allOf(IsMapContaining.hasKey(one));
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
