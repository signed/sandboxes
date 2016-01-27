package com.github.signed.swagger.essentials;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import io.swagger.models.parameters.Parameter;

public class ParameterMatcher extends TypeSafeDiagnosingMatcher<Parameter> {

    public static Matcher<? super Parameter> parameterNamed(String parameterName){
        return new ParameterMatcher().withName(parameterName);
    }

    private Matcher<String> nameMatcher = Matchers.any(String.class);

    @Override
    protected boolean matchesSafely(Parameter item, Description mismatchDescription) {
        boolean nameMatches = nameMatcher.matches(item.getName());
        if(!nameMatches){
            mismatchDescription.appendText("parameter name ").appendValue(item.getName());
        }
        return nameMatches;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("parameter ");
        description.appendText("name ").appendDescriptionOf(nameMatcher);

    }

    private ParameterMatcher withName(String tagName) {
        nameMatcher = Matchers.equalTo(tagName);
        return this;
    }
}
