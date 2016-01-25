package com.github.signed.swagger.essentials;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import io.swagger.models.Tag;

public class TagMatcher extends TypeSafeDiagnosingMatcher<Tag> {

    public static Matcher<? super Tag> tagNamed(String tagName){
        return new TagMatcher().withName(tagName);
    }

    private Matcher<String> nameMatcher = Matchers.any(String.class);

    @Override
    protected boolean matchesSafely(Tag item, Description mismatchDescription) {
        boolean nameMatches = nameMatcher.matches(item.getName());
        if(!nameMatches){
            mismatchDescription.appendText("tag name ").appendValue(item.getName());
        }
        return nameMatches;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("tag ");
        description.appendText("name ").appendDescriptionOf(nameMatcher);

    }

    private Matcher<Tag> withName(String tagName) {
        nameMatcher = Matchers.equalTo(tagName);
        return this;
    }
}
