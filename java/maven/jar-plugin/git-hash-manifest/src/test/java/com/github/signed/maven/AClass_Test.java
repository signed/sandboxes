package com.github.signed.maven;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AClass_Test {

    @Test
    public void ensureResourceIsStillCopiedToTargetFolder() throws Exception {
        URL resource = AClass.class.getResource("/exclude.properties");
        assertThat(resource, CoreMatchers.not(nullValue()));
    }
}
