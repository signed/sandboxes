package com.github.signed.pmd.abstractions;

import com.github.signed.pmd.CodeModelToPmdAst;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import org.javacc.parser.JavaCCParser;

import java.io.IOException;

public class SingletonAccessBuilder {

    private final CodeModelToPmdAst converter = new CodeModelToPmdAst();
    private final JDefinedClass singleton;
    private final JMethod instanceMethod;
    private final JCodeModel model;

    public SingletonAccessBuilder(JDefinedClass singleton, JMethod instanceMethod, JCodeModel model) {
        this.singleton = singleton;
        this.instanceMethod = instanceMethod;
        this.model = model;
    }

    public void invokeInstanceMethodInAnyMethod() throws JClassAlreadyExistsException {
        JDefinedClass singletonAccessor = model._class("apackage.SingletonAccess");
        JMethod methodWithSingletonAccess = singletonAccessor.method(JavaCCParser.ModifierSet.PUBLIC, model.VOID, "doStuff");
        methodWithSingletonAccess.body().add(instanceMethodCall());
    }

    public void invokeInstanceMethodAndAssignedToLocalVariableInAnyMethod() throws JClassAlreadyExistsException{
        JDefinedClass singletonAccessor = model._class("apackage.SingletonAccess");
        JMethod methodWithSingletonAccess = singletonAccessor.method(JavaCCParser.ModifierSet.PUBLIC, model.VOID, "doStuff");
        JBlock body = methodWithSingletonAccess.body();
        body.decl(singleton, "instance", instanceMethodCall());
    }

    private JInvocation instanceMethodCall() {
        return singleton.staticInvoke(instanceMethod);
    }

    public JavaNode toPmdAst() throws IOException, PMDException {
        return converter.convertClassToJavaNode("SingletonAccess", model);
    }
}
