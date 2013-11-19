package com.github.signed.pmd;

import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.LanguageVersionHandler;
import net.sourceforge.pmd.lang.Parser;
import net.sourceforge.pmd.lang.java.ast.JavaNode;

import java.io.StringReader;

public class AstParser {

    public JavaNode parse(String sourceCode) {
        LanguageVersionHandler languageVersionHandler = LanguageVersion.JAVA_17.getLanguageVersionHandler();
        Parser parser = languageVersionHandler.getParser(languageVersionHandler.getDefaultParserOptions());
        JavaNode node = (JavaNode) parser.parse(null, new StringReader(sourceCode));
        languageVersionHandler.getSymbolFacade().start(node);
        languageVersionHandler.getTypeResolutionFacade(null).start(node);
        return node;
    }
}
