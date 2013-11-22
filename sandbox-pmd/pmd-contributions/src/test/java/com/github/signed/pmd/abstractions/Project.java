package com.github.signed.pmd.abstractions;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import org.javacc.parser.JavaCCParser;

public class Project {
    private final JCodeModel model = new JCodeModel();
    private JMethod instanceMethod;
    private JDefinedClass singleton;

    public void writeASingleton() throws JClassAlreadyExistsException {
        singleton = model._class("singletons.Singleton");
        instanceMethod = singleton.method(JavaCCParser.ModifierSet.PUBLIC | JavaCCParser.ModifierSet.STATIC, singleton, "instance");
        instanceMethod.body()._return(JExpr._new(singleton));

    }

    public SingletonAccessBuilder anotherClassThatAccessesTheSingleton() {
        return new SingletonAccessBuilder(singleton, instanceMethod, model);
    }
}
