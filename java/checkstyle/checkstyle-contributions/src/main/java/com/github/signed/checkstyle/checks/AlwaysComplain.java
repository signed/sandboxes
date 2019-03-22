package com.github.signed.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AlwaysComplain extends Check {
    @Override
    public int[] getDefaultTokens() {
        int[] ints = {TokenTypes.METHOD_CALL};
        return ints;
    }

    @Override
    public void visitToken(DetailAST aAST) {
        log(aAST, "I do not like it...");
    }
}
