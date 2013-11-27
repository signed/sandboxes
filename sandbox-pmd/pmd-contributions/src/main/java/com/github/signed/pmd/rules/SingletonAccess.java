package com.github.signed.pmd.rules;

import com.github.signed.pmd.abstractions.MethodCallExtractor;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class SingletonAccess extends AbstractJavaRule{

    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        try{
        MethodCallExtractor methodCallExtractor = new MethodCallExtractor();
        node.jjtAccept(methodCallExtractor, data);
        }catch(Exception any){
            addViolationWithMessage(data, node, "exception");
        }
        return data;
    }

}
