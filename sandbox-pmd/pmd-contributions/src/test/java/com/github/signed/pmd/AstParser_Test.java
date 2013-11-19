package com.github.signed.pmd;

import com.github.signed.pmd.rules.AlwaysComplain;
import com.google.common.io.Files;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.nio.charset.Charset;

public class AstParser_Test {


    private final File file = new File("project-under-the-microscope/business/src/main/java/singletons/AccessSingleton.java");
    private String sourceCode;

    @Before
    public void setUp() throws Exception {
        sourceCode = Files.toString(file, Charset.forName("UTF-8"));
    }

    @Test
    public void testName() throws Exception {
        JavaNode ast = new AstParser().parse(sourceCode);

        ast.jjtAccept(new AlwaysComplain(), null);
    }


    @Test
    public void testName2() throws Exception {
        Report report = new Report();
        PMD p = new PMD();
        p.getConfiguration().setDefaultLanguageVersion(LanguageVersion.JAVA_17);
        RuleContext ctx = new RuleContext();
        ctx.setReport(report);
        ctx.setSourceCodeFilename("n/a");
        ctx.setLanguageVersion(LanguageVersion.JAVA_17);
//        ctx.setIgnoreExceptions(false);
        RuleSet rules = new RuleSet();
        rules.addRule(new AlwaysComplain());
        p.getSourceCodeProcessor().processSourceCode(new StringReader(sourceCode), new RuleSets(rules), ctx);
        System.out.println(report);
    }
}
