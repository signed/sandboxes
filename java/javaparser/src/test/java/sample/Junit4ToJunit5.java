package sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;

public class Junit4ToJunit5 {

    @Test
    public void name() throws IOException {
        CompilationUnit test = JavaParser.parse(Paths.get("src/test/java/sample/JunitTest.java"));

        CompilationUnit preparedForModification = LexicalPreservingPrinter.setup(test);

        new SetupMethodMigration().visit(preparedForModification, null);
        new TestMethodMigration().visit(preparedForModification, null);
        new ReduceToDefaultScope().visit(preparedForModification, new ReduceToDefaultScope.Accumulator());

        StringWriter writer = new StringWriter();
        LexicalPreservingPrinter.print(preparedForModification, writer);

        System.out.println(writer.toString());
    }
}
