package com.github.signed.cucumber;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class Hello_Test {

    @Test
    public void randomTest() throws Exception {
        Hello hello = new Hello("Mahlzeit");
        MatcherAssert.assertThat(hello.sayHi(), is("Mahlzeit World"));

    }
}