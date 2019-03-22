package com.github.signed.pmd.rules;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class AlwaysComplain extends AbstractJavaRule{

    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        if("AccessSingleton".equals(node.getImage())){
            addViolationWithMessage(data, node, "bite me", 0,7);
        }
        return data;
    }
}
