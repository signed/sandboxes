package com.github.signed.pmd.abstractions;

import com.beust.jcommander.internal.Lists;
import net.sourceforge.pmd.lang.java.ast.JavaParserVisitorAdapter;

import java.util.List;

public class MethodCallExtractor extends JavaParserVisitorAdapter {
    private final List<MethodCall> methodCalls = Lists.newArrayList();

    public List<MethodCall> methods() {
        methodCalls.add(new MethodCall());
        return methodCalls;
    }
}
