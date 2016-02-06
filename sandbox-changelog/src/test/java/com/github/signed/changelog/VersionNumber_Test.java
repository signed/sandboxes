package com.github.signed.changelog;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class VersionNumber_Test {

    @Test
    public void format_properly() throws Exception {
        VersionNumber versionNumber = VersionNumber.SemVer(14, 8, 33);
        assertThat(versionNumber.asString(), is("14.8.33"));
    }
}