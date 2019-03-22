package com.github.signed.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SingletonAccess_Test {

    @Test
    public void theCheckIsInterestedInMethodCalls() throws Exception {
        SingletonAccess singletonAccess = new SingletonAccess();
        assertThat(singletonAccess.getDefaultTokens()[0], is(TokenTypes.METHOD_CALL));
    }
}
