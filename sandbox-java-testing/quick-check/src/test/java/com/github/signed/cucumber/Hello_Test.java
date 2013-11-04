package com.github.signed.cucumber;

import com.pholser.junit.quickcheck.ForAll;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class Hello_Test {

    @Theory public void anyIntegerIsGreaterThanSeven(@ForAll int value) throws Exception {
        MatcherAssert.assertThat(value, Matchers.greaterThan(7));
    }
}