package com.github.signed.sanbox.vavr;

import io.vavr.control.Try;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class SamplesTest {

    @Test
    public void if_an_exception_occurs_empty_is_returned() throws Exception {
        Try<Integer> divide = divide(1, 0);
        assertThat("there was an exception, should be empty", divide.isEmpty());

    }

    Try<Integer> divide(int one, int two) {
        return Try.of(() -> one / two);
    }
}
