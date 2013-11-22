package com.github.signed.pmd.abstractions;

import com.beust.jcommander.internal.Maps;
import com.github.signed.pmd.AstParser;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JPackage;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import org.hamcrest.Matchers;
import org.javacc.parser.JavaCCParser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodCallExtractor_Test {

    private final Project project = new Project();
    private final MethodCallExtractor extractor = new MethodCallExtractor();


    public static class Project {
        private final JCodeModel model = new JCodeModel();
        private JMethod instanceMethod;
        private JInvocation getSingletInstance;

        public void withSingleton() throws JClassAlreadyExistsException {
            JDefinedClass singleton = model._class("singletons.Singleton");
            instanceMethod = singleton.method(JavaCCParser.ModifierSet.PUBLIC | JavaCCParser.ModifierSet.STATIC, singleton, "instance");
            instanceMethod.body()._return(JExpr._new(singleton));
            getSingletInstance =  singleton.staticInvoke(instanceMethod);
        }

        public JInvocation singletonInstanceInvocation(){
            return getSingletInstance;
        }
    }

    @Before
    public void setUp() throws Exception {
        project.withSingleton();

        JDefinedClass singletonAccessor = project.model._class("apackage.SingletonAccess");
        JMethod methodWithSingletonAccess = singletonAccessor.method(JavaCCParser.ModifierSet.PUBLIC, project.model.VOID, "doStuff");
        methodWithSingletonAccess.body().add(project.singletonInstanceInvocation());
    }

    @Test
    public void extractTheMethodInvocation() throws Exception {
        extractMethodCallsInClass("SingletonAccess");

        assertThat(extractor.methods(), Matchers.hasSize(1));
    }

    @Test
    public void extractTheNameOfTheInvokedMethod() throws Exception {
        extractMethodCallsInClass("SingletonAccess");

        assertThat(extractor.methods().get(0).name(), is("instance"));
    }

    private void extractMethodCallsInClass(String className) throws IOException, PMDException {
        InMemoryCodeWriter writer = new InMemoryCodeWriter();
        project.model.build(writer);
        JavaNode classWithSingletonAccess = new AstParser().parse(writer.getSourceForClass(className));

        classWithSingletonAccess.jjtAccept(extractor, null);
    }

    public static class InMemoryCodeWriter extends CodeWriter {

        private final Map<String, ByteArrayOutputStream> remember = Maps.newHashMap();
        private final Map<String, String> sourceCodeByClassName = Maps.newHashMap();


        public InMemoryCodeWriter() {
            encoding = "UTF-8";
        }

        @Override
        public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
            ByteArrayOutputStream current = new ByteArrayOutputStream();
            remember.put(fileName, current);

            return current;
        }

        @Override
        public void close() throws IOException {
            for (Map.Entry<String, ByteArrayOutputStream> entry : remember.entrySet()) {
                ByteArrayOutputStream value = entry.getValue();
                sourceCodeByClassName.put(entry.getKey(), value.toString(this.encoding));
                value.close();
            }
            remember.clear();
        }

        public String getSourceForClass(String className) {
            return sourceCodeByClassName.get(className + ".java");
        }
    }
}
