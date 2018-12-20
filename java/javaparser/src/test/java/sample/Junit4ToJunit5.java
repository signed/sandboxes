package sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Junit4ToJunit5 {

    @Test
    public void name() throws IOException {
        Path sampleFile = Paths.get("src/test/java/sample/JunitTest.java");
        CompilationUnit test = JavaParser.parse(sampleFile);

        CompilationUnit preparedForModification = LexicalPreservingPrinter.setup(test);

        new SetupMethodMigration().visit(preparedForModification, null);
        new TestMethodMigration().visit(preparedForModification, null);
        new ReduceToDefaultScope().visit(preparedForModification, new ReduceToDefaultScope.Accumulator());

        StringWriter writer = new StringWriter();
        LexicalPreservingPrinter.print(preparedForModification, writer);

        String transformedSourceCode = writer.toString();
        System.out.println(transformedSourceCode);
        Files.write(sampleFile, transformedSourceCode.getBytes(Charset.forName("UTF-8")));
    }
}
