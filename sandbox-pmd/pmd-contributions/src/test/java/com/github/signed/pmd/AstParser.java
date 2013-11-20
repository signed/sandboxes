package com.github.signed.pmd;

import com.github.signed.pmd.rules.AlwaysComplain;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.LanguageVersionHandler;
import net.sourceforge.pmd.lang.Parser;
import net.sourceforge.pmd.lang.VisitorStarter;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.ast.ParseException;
import net.sourceforge.pmd.util.IOUtil;

import java.io.StringReader;

public class AstParser {

    public JavaNode parse(String source) throws PMDException {
        Report report = new Report();

        RuleContext ctx = new RuleContext();
        ctx.setReport(report);
        ctx.setSourceCodeFilename("n/a");
        ctx.setLanguageVersion(LanguageVersion.JAVA_17);

        RuleSet rules = new RuleSet();
        rules.addRule(new AlwaysComplain());

        PMDConfiguration configuration = new PMDConfiguration();
        configuration.setDefaultLanguageVersion(LanguageVersion.JAVA_17);

        StringReader sourceCode = new StringReader(source);
        try {
            LanguageVersion languageVersion = ctx.getLanguageVersion();
            LanguageVersionHandler languageVersionHandler = languageVersion.getLanguageVersionHandler();
            Parser parser = PMD.parserFor(languageVersion, configuration);
            Node rootNode = parser.parse(ctx.getSourceCodeFilename(), sourceCode);
            languageVersionHandler.getSymbolFacade().start(rootNode);
            languageVersion.getLanguageVersionHandler().getDataFlowFacade().start(rootNode);
            VisitorStarter typeResolutionFacade = languageVersion.getLanguageVersionHandler().getTypeResolutionFacade(configuration.getClassLoader());
            typeResolutionFacade.start(rootNode);
            return (JavaNode) rootNode;
        } catch (ParseException pe) {
            throw new PMDException("Error while parsing " + ctx.getSourceCodeFilename(), pe);
        } catch (Exception e) {
            throw new PMDException("Error while processing " + ctx.getSourceCodeFilename(), e);
        } finally {
            IOUtil.closeQuietly(sourceCode);
        }
    }
}
