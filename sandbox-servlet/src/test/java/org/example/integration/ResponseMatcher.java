package org.example.integration;

import javax.ws.rs.core.Response;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ResponseMatcher extends TypeSafeDiagnosingMatcher<Response> {

    @Factory
    public static Matcher<Response> responseWithStatus(Matcher<Response.StatusType> statusMatcher){
        return new ResponseMatcher(statusMatcher);
    }

    private final Matcher<Response.StatusType> statusMatcher;

    public ResponseMatcher(Matcher<Response.StatusType> statusMatcher) {
        this.statusMatcher = statusMatcher;
    }

    @Override
    protected boolean matchesSafely(Response item, Description mismatchDescription) {
        statusMatcher.describeMismatch(item.getStatusInfo(), mismatchDescription);
        return statusMatcher.matches(item.getStatusInfo());
    }

    @Override
    public void describeTo(Description description) {
        statusMatcher.describeTo(description);
    }
}
