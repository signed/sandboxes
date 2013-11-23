package com.github.signed.pmd.abstractions;

import com.google.common.base.Function;
import com.sun.codemodel.JClassAlreadyExistsException;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class MethodCallExtractor_Test {

    private final Function<SingletonAccessBuilder, Void> function;

    @Parameters(name = "{index}: fib({0})={1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new Function<SingletonAccessBuilder, Void>() {
                    @Override
                    public Void apply(SingletonAccessBuilder input) {
                        try {
                            input.invokeInstanceMethodInAnyMethod();
                        } catch (JClassAlreadyExistsException e) {
                            throw new RuntimeException();
                        }
                        return null;
                    }
                } },
                { new Function<SingletonAccessBuilder, Void>() {
                    @Override
                    public Void apply(SingletonAccessBuilder input) {
                        try {
                            input.invokeInstanceMethodAndAssignedToLocalVariableInAnyMethod();
                        } catch (JClassAlreadyExistsException e) {
                            throw new RuntimeException();
                        }
                        return null;
                    }
                } }
        });
    }

    private final Project project = new Project();
    private final MethodCallExtractor extractor = new MethodCallExtractor();
    private SingletonAccessBuilder singletonAccess;

    public MethodCallExtractor_Test(Function<SingletonAccessBuilder, Void> function){
        this.function = function;
    }

    @Before
    public void setUp() throws Exception {
        project.writeASingleton();
        singletonAccess = project.anotherClassThatAccessesTheSingleton();
        function.apply(singletonAccess);
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

    @Test
    public void extractTheFullQualifiedClassNameThatDeclaresTheInvokedMethod() throws Exception {
        extractMethodCallsFromTheSingletonAccessClass();

        assertThat(extractor.methods().get(0).classMethodIsDeclaredIn(), is("singletons.Singleton"));
    }

    private void extractMethodCallsFromTheSingletonAccessClass() throws IOException, PMDException {
        JavaNode classWithSingletonAccess = singletonAccess.toPmdAst();
        classWithSingletonAccess.jjtAccept(extractor, null);
    }
}