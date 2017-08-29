package com.github.signed.sanbox.vavr;

import io.vavr.API;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SamplesTest {

    @Test
    public void if_an_exception_occurs_empty_is_returned() throws Exception {
        assertThat("there was an exception, should be empty", divide(1, 0).isEmpty());
    }

    @Test
    public void if_computation_succeeds_the_value_is_returned() throws Exception {
        assertThat(divide(2, 2).get(), equalTo(1));
    }

    @Test
    public void leftMeansError() throws Exception {
        assertThat(left("doom").map(s -> s + "map").getOrElse("fallback"), equalTo("fallback"));
    }

    @Test
    public void rightMeansValue() throws Exception {
        assertThat(right("boom").map(s -> s + "map").getOrElse("fallback"), equalTo("boommap"));
    }

    @Test(expected = IllegalStateException.class)
    public void map_failure() throws Exception {
        Try.<String>failure(new RuntimeException("flup"))
                .recover(IllegalStateException.class, Throwable::getMessage)
                .mapFailure(Case($(instanceOf(RuntimeException.class)), (Function<RuntimeException, IllegalStateException>) IllegalStateException::new))
                .get();

    }

    @Test
    public void just_return_the_success_value() throws Exception {
        Try<String> success = Try.success("success");

        String actual = success
                .recover(IllegalStateException.class, Throwable::getMessage)
                .mapFailure(Case($(instanceOf(RuntimeException.class)), (Function<RuntimeException, IllegalStateException>) IllegalStateException::new))
                .get();

        assertThat(actual, equalTo("success"));
    }

    @Test
    public void case_example() throws Exception {
        String s = Match(1).of(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(), "?")
        );

        assertThat(s, equalTo("one"));
    }

    private Try<Integer> divide(int one, int two) {
        return Try.of(() -> one / two);
    }
}
