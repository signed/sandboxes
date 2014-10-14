package com.github.signed.maven;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AClass_Test {
    @Test
    public void ensureResourceIsStillCopiedToTargetFolder() throws Exception {
        assertThat(new AClass().getString(), is("42"));
    }
}