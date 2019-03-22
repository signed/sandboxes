package com.github.signed.pmd.abstractions;

import com.github.signed.pmd.CodeModelToPmdAst;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.lang.java.ast.JavaNode;

import java.io.IOException;

public class ClassBuilder {
    private final CodeModelToPmdAst converter = new CodeModelToPmdAst();
    private final JCodeModel model;
    private JDefinedClass jDefinedClass;

    public ClassBuilder(JCodeModel model) {
        this.model = model;
    }

    public ClassBuilder anyClass() throws JClassAlreadyExistsException {
        jDefinedClass = model._class("some.Classz");
        return this;
    }

    public JMethod anyMethod() {
        return jDefinedClass.method(0, model.VOID, "anyName");
    }

    public JavaNode toPmdAst() throws IOException, PMDException {
        return converter.convertClassToJavaNode("Classz", model);
    }
}
