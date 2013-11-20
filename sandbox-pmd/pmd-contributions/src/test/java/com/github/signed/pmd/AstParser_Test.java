package com.github.signed.pmd;

import com.github.signed.pmd.rules.AlwaysComplain;
import com.google.common.io.Files;
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
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;

public class AstParser_Test {
    private final File file = new File("project-under-the-microscope/business/src/main/java/singletons/AccessSingleton.java");
    private String sourceCode;
    private AstParser astParser = new AstParser();

    @Before
    public void setUp() throws Exception {
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
    public void testName() throws Exception {
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
        System.out.println(gClass.toCode());

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
        System.out.println(report);
    }
}
