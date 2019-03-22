package com.github.signed.pmd.abstractions;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JType;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;


public class MethodCallExtractor_ConstructorCallsTest {

    private final Project project = new Project();
    private final MethodCallExtractor extractor = new MethodCallExtractor();
    private final ClassBuilder classBuilder = project.anyClass();

    @Before
    public void setUp() throws Exception {
        JBlock body = classBuilder.anyMethod().body();
        JType stringBuilderType = project.reference(StringBuilder.class);
        body.decl(stringBuilderType, "builder", JExpr._new(stringBuilderType));
    }

    @Test
    public void ignoreConstructorCalls() throws Exception {
        extractMethodCallsFromTheSingletonAccessClass();

        assertThat(extractor.methods(), Matchers.hasSize(0));
    }

    private void extractMethodCallsFromTheSingletonAccessClass() throws IOException, PMDException {
        JavaNode classWithSingletonAccess = classBuilder.toPmdAst();
        classWithSingletonAccess.jjtAccept(extractor, null);
    }
}