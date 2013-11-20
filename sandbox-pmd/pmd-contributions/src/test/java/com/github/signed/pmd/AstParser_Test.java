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
    private AstParser astParser = new AstParser();

    @Before
    public void setUp() throws Exception {
        sourceCode = Files.toString(file, Charset.forName("UTF-8"));
    }

    @Test
    public void justParseToRootNode() throws Exception {
        JavaNode node = astParser.parse(sourceCode);
        System.out.println(node);
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
