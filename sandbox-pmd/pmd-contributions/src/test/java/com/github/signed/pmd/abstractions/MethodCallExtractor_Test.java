package com.github.signed.pmd.abstractions;

import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodCallExtractor_Test {

    private final Project project = new Project();
    private final MethodCallExtractor extractor = new MethodCallExtractor();
    private SingletonAccessBuilder singletonAccess;

    @Before
    public void setUp() throws Exception {
        project.writeASingleton();
        singletonAccess = project.anotherClassThatAccessesTheSingleton();
        singletonAccess.invokeInstanceMethodInAnyMethod();
    }

    @Test
    public void extractTheMethodInvocation() throws Exception {
        extractMethodCallsFromTheSingletonAccessClass();

        assertThat(extractor.methods(), Matchers.hasSize(1));
    }

    @Test
    public void extractTheNameOfTheInvokedMethod() throws Exception {
        extractMethodCallsFromTheSingletonAccessClass();

        assertThat(extractor.methods().get(0).name(), is("instance"));
    }

    private void extractMethodCallsFromTheSingletonAccessClass() throws IOException, PMDException {
        JavaNode classWithSingletonAccess = singletonAccess.toPmdAst();
        classWithSingletonAccess.jjtAccept(extractor, null);
    }
}