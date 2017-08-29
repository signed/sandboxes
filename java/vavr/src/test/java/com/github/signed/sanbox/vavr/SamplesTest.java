package com.github.signed.sanbox.vavr;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

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

    private Try<Integer> divide(int one, int two) {
        return Try.of(() -> one / two);
    }
}
