package com.github.signed.sanbox.vavr;

import io.vavr.control.Try;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SamplesTest {

    @Test
    public void if_an_exception_occurs_empty_is_returned() throws Exception {
        assertThat("there was an exception, should be empty", divide(1, 0).isEmpty());
    }

    @Test
    public void if_computation_succeeds_the_value_is_returned() throws Exception {
        assertThat(divide(1,1).get(), equalTo(1));
    }

    Try<Integer> divide(int one, int two) {
        return Try.of(() -> one / two);
    }
}
