package com.github.signed.pmd;

import com.github.signed.pmd.abstractions.InMemoryCodeWriter;
import com.sun.codemodel.JCodeModel;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.lang.java.ast.JavaNode;

import java.io.IOException;

public class CodeModelToPmdAst {
    public JavaNode convertClassToJavaNode(String type, JCodeModel model) throws IOException, PMDException {
        InMemoryCodeWriter writer = new InMemoryCodeWriter();
        model.build(writer);
        String sourceCode = writer.getSourceForClass(type);
        System.out.println(sourceCode);
        return new AstParser().parse(sourceCode);
    }
}
