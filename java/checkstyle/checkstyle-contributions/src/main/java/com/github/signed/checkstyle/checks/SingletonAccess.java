package com.github.signed.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SingletonAccess extends Check{
    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_CALL};
    }

    @Override
    public void beginTree(DetailAST aRootAST) {
        System.out.println(aRootAST);
    }

    @Override
    public void visitToken(DetailAST aAST) {
        System.out.println("aAST = " + aAST);
    }

    @Override
    public void finishTree(DetailAST aRootAST) {
        System.out.println("aRootAST = " + aRootAST);
    }
}
