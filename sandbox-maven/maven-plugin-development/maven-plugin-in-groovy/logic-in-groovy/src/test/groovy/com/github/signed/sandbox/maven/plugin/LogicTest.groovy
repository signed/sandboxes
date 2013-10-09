package com.github.signed.sandbox.maven.plugin

import org.junit.Test

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

class LogicTest {

    @Test
    void 'do the funny stuff'(){
        assertThat(new Logic().executeLogic(), equalTo('The logic tells you 42'))
    }

    @Test
    void 'do the funny stuff again but call it different'(){
        assertThat(new Logic().executeLogic(), equalTo('The logic tells you 42'))
    }
}