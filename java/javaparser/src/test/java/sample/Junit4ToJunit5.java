package sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.Optional;

public class Junit4ToJunit5 {

    public static class SetupMethodMigration extends ModifierVisitor<Void> {
        @Override
        public Node visit(ImportDeclaration n, Void arg) {
            ImportDeclarations.replace(n, BeforeClass.class, org.junit.jupiter.api.BeforeAll.class);
            ImportDeclarations.replace(n, Before.class, org.junit.jupiter.api.BeforeEach.class);
            ImportDeclarations.replace(n, After.class, org.junit.jupiter.api.AfterEach.class);
            ImportDeclarations.replace(n, AfterClass.class, org.junit.jupiter.api.AfterAll.class);
            return n;
        }

        @Override
        public Visitable visit(MethodDeclaration n, Void arg) {
            Annotations.replace(n, BeforeClass.class, BeforeAll.class);
            Annotations.replace(n, Before.class, BeforeEach.class);
            Annotations.replace(n, After.class, AfterEach.class);
            Annotations.replace(n, AfterClass.class, AfterAll.class);
            return n;
        }
    }

    public static class TestMethodMigration extends ModifierVisitor<Void> {

        @Override
        public Node visit(ImportDeclaration n, Void arg) {
            ImportDeclarations.replace(n, Test.class, org.junit.jupiter.api.Test.class);
            return n;
        }

        @Override
        public Visitable visit(MethodDeclaration n, Void arg) {
            Optional<AnnotationExpr> annotationByClass = n.getAnnotationByClass(Test.class);
            annotationByClass.ifPresent(it -> n.setModifier(Modifier.PUBLIC, false));
            return n;
        }
    }

    @Test
    public void name() throws IOException {
        CompilationUnit test = JavaParser.parse(Paths.get("src/test/java/sample/JunitTest.java"));

        CompilationUnit preparedForModification = LexicalPreservingPrinter.setup(test);

        new SetupMethodMigration().visit(preparedForModification, null);
        new TestMethodMigration().visit(preparedForModification, null);

        StringWriter writer = new StringWriter();
        LexicalPreservingPrinter.print(preparedForModification, writer);

        System.out.println(writer.toString());
    }
}
