package com.github.signed.maven;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnitTest {
    @Test
    public void unitTest() {
        assertThat(new AClass().getString(), is("42"));
    }
}