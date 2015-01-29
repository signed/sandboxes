package org.example.integration;

import javax.ws.rs.core.Response;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class StatusMatcher extends TypeSafeDiagnosingMatcher<Response.StatusType> {

    @Factory
    public static Matcher<Response.StatusType> NotFound() {
        return new StatusMatcher(Response.Status.NOT_FOUND);
    }

    private final Response.StatusType expected;

    public StatusMatcher(Response.StatusType expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Response.StatusType item, Description mismatchDescription) {
        boolean match = expected.getStatusCode() == item.getStatusCode();
        append(mismatchDescription, item);
        return match;
    }

    @Override
    public void describeTo(Description description) {
        Response.StatusType status = expected;
        append(description, status);
    }

    private void append(Description description, Response.StatusType status) {
        description.appendValue(status.getStatusCode() + " (" + status.getReasonPhrase() + ")");
    }
}
