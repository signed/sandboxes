package com.github.signed.pmd;

import com.github.signed.pmd.rules.AlwaysComplain;
import com.google.common.io.Files;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.writer.SingleStreamCodeWriter;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import org.javacc.parser.JavaCCParser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;

public class AstParser_Test {
    private String sourceCode;
    private AstParser astParser = new AstParser();

    @Before
    public void setUp() throws Exception {
        File file;
        if( new File("do not care").getAbsoluteFile().getParentFile().getName().equals("sandbox-pmd")){
            file = new File("project-under-the-microscope/business/src/main/java/singletons/AccessSingleton.java");
        }else{
            file = new File("../project-under-the-microscope/business/src/main/java/singletons/AccessSingleton.java");
        }
        sourceCode = Files.toString(file, Charset.forName("UTF-8"));
    }

    @Test
    public void justParseToRootNode() throws Exception {
        JavaNode node = astParser.parse(sourceCode);
        List<ASTPrimaryExpression> childrenOfType = node.findChildrenOfType(ASTPrimaryExpression.class);
        List<ASTPackageDeclaration> packageDeclarations = node.findChildrenOfType(ASTPackageDeclaration.class);

        System.out.println(node);
    }

    @Test
    public void joist() throws Exception {
        GClass singleton = new GClass("singletons.Singleton");
        GMethod instanceMethod = singleton.getMethod("instance");
        instanceMethod.returnType("singletons.Singleton");
        instanceMethod.setStatic();
        instanceMethod.setBody("return new Singleton()");

        System.out.println(singleton.toCode());
        GClass gClass = new GClass("apackage.DoNotCare");
        gClass.addImports("singletons.Singleton");
        GMethod method = gClass.getMethod("doNotCare");
        method.setBody("Singleton.instance()");
    }

    @Test
    public void codeModel() throws Exception {
        JCodeModel model = new JCodeModel();
        JDefinedClass singleton = model._class("singletons.Singleton");
        JMethod instanceMethod = singleton.method(JavaCCParser.ModifierSet.PUBLIC| JavaCCParser.ModifierSet.STATIC, singleton, "instance");
        instanceMethod.body()._return(JExpr._new(singleton));

        JDefinedClass singletonAccessor = model._class("apackage.DoNotCare");
        JMethod methodWithSingletonAccess = singletonAccessor.method(JavaCCParser.ModifierSet.PUBLIC, model.VOID, "doStuff");
        methodWithSingletonAccess.body().add(singleton.staticInvoke(instanceMethod));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CodeWriter codeWriter = new SingleStreamCodeWriter(out);
        model.build(codeWriter);
    }

    @Test
    public void runIntegrationTestWithPMD() throws Exception {
        Report report = new Report();
        PMD pmd = new PMD();
        pmd.getConfiguration().setDefaultLanguageVersion(LanguageVersion.JAVA_17);
        RuleContext ctx = new RuleContext();
        ctx.setReport(report);
        ctx.setSourceCodeFilename("n/a");
        ctx.setLanguageVersion(LanguageVersion.JAVA_17);
//        ctx.setIgnoreExceptions(false);
        RuleSet rules = new RuleSet();
        rules.addRule(new AlwaysComplain());
        pmd.getSourceCodeProcessor().processSourceCode(new StringReader(sourceCode), new RuleSets(rules), ctx);
    }
}