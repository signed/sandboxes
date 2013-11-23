package com.github.signed.pmd.abstractions;

import com.beust.jcommander.internal.Lists;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.ast.JavaParserVisitorAdapter;

import java.util.List;
import java.util.regex.Pattern;

public class MethodCallExtractor extends JavaParserVisitorAdapter {
    private final List<MethodCall> methodCalls = Lists.newArrayList();


    @Override
    public Object visit(ASTPrimaryExpression node, Object data) {
        addCall(node);
        return super.visit(node, data);
    }

    public List<MethodCall> methods() {
        return methodCalls;
    }

    private void addCall(ASTPrimaryExpression node) {
        final ASTPrimarySuffix suffix = node.getFirstChildOfType(ASTPrimarySuffix.class);
        if (suffix.hasDescendantOfType(ASTArguments.class)) {
            final ASTPrimaryPrefix prefix = node.getFirstChildOfType(ASTPrimaryPrefix.class);
            final ASTName methodName = prefix.getFirstChildOfType(ASTName.class);

            methodCalls.add(new MethodCall() {
                @Override
                public String name() {
                    return methodName();
                }

                private String methodName() {
                    return expression()[1];
                }

                private String[] expression() {
                    return methodName.getImage().split(Pattern.quote("."));
                }

                @Override
                public String classMethodIsDeclaredIn() {
                    return "singletons."+ declaringClass();
                }

                private String declaringClass() {
                    return expression()[0];
                }
            });
        }
    }
}
