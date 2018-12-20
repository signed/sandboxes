package sample;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import org.junit.Test;

import java.util.Optional;

public class TestMethodMigration extends ModifierVisitor<Void> {

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
