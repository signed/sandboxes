package com.github.signed.pmd.rules;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class AlwaysComplain extends AbstractJavaRule{

    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        System.out.println("\nhello world\n");
        return data;
    }
}
