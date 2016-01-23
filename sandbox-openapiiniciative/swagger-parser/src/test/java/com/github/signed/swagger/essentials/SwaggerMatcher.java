package com.github.signed.swagger.essentials;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;

public class SwaggerMatcher extends TypeSafeDiagnosingMatcher<Swagger> {

    public static Matcher<? super Swagger> hasPathDefinitionsFor(String ... paths) {
        return new SwaggerMatcher().hasPaths(paths);
    }

    public static Matcher<? super Swagger> hasModelDefinitionsFor(String ... definitionIdentifier) {
        return new SwaggerMatcher().hasDefinitions(definitionIdentifier);
    }

    public static Matcher<? super Swagger> hasTagDefinitionsFor(String ... tagDefinitions) {
        return new SwaggerMatcher().hasTagDefinitions(tagDefinitions);
    }

    private Matcher<Map<String, Path>> pathsMatcher = new BaseMatcher<Map<String, Path>>() {
        @Override
        public boolean matches(Object item) {
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("any paths");
        }
    };


    private Matcher<Map<String, Model>> modelDefinitionsMatcher = new BaseMatcher<Map<String, Model>>() {
        @Override
        public boolean matches(Object item) {
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("any model definitions");
        }
    };

    private Matcher<Iterable<?super Tag>> tagDefinitionsMatcher = new BaseMatcher<Iterable<? super Tag>>() {
        @Override
        public boolean matches(Object item) {
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("any tag definition");
        }
    };


    public Matcher<Swagger> hasPaths(String ... paths) {
        pathsMatcher = AllOf.allOf(asList(paths).stream().map(IsMapContaining::hasKey).collect(toList()));
        return this;
    }

    public Matcher<? super Swagger> hasDefinitions(String ... definitionIdentifier) {
        modelDefinitionsMatcher = AllOf.allOf(asList(definitionIdentifier).stream().map(IsMapContaining::hasKey).collect(toList()));
        return this;
    }

    private Matcher<? super Swagger> hasTagDefinitions(String ... tagDefinitions) {
        Matcher<Iterable<? super Tag>> one = Matchers.hasItem(TagMatcher.tagNamed(tagDefinitions[0]));
        Matcher<Iterable<? super Tag>> two = Matchers.hasItem(TagMatcher.tagNamed(tagDefinitions[1]));
        tagDefinitionsMatcher = Matchers.allOf(one, two);
        return this;
    }

    @Override
    protected boolean matchesSafely(Swagger swagger, Description mismatchDescription) {
        boolean requiredPathsExist = pathsMatcher.matches(swagger.getPaths());
        if (!requiredPathsExist) {
            pathsMatcher.describeMismatch(swagger.getPaths(), mismatchDescription);
        }

        boolean definitionsMatch = modelDefinitionsMatcher.matches(swagger.getDefinitions());
        if (!definitionsMatch) {
            modelDefinitionsMatcher.describeMismatch(swagger.getDefinitions(), mismatchDescription);
        }

        boolean tagDefinitionsMatch = tagDefinitionsMatcher.matches(swagger.getTags());
        if(!tagDefinitionsMatch){
            tagDefinitionsMatcher.describeMismatch(swagger.getTags(), mismatchDescription);
        }

        return requiredPathsExist && definitionsMatch && tagDefinitionsMatch;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(pathsMatcher).appendText(" ").appendDescriptionOf(modelDefinitionsMatcher).appendText(" ").appendDescriptionOf(tagDefinitionsMatcher);
    }
}
