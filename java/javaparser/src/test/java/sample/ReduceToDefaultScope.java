package sample;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.lang.annotation.Annotation;

public class ReduceToDefaultScope extends GenericVisitorAdapter<Void, ReduceToDefaultScope.Accumulator> {
    public static class Accumulator {
        public boolean containsTests = false;
    }

    @Override
    public Void visit(ClassOrInterfaceDeclaration n, Accumulator arg) {
        Void visit = super.visit(n, arg);
        if (arg.containsTests) {
            n.removeModifier(Modifier.PUBLIC);
        }
        return visit;
    }

    @Override
    public Void visit(MethodDeclaration method, Accumulator arg) {
        Void visit = super.visit(method, arg);
        method.getAnnotationByClass(org.junit.jupiter.api.Test.class).ifPresent(__ -> arg.containsTests = true);
        reduceToDefaultScopeIfAnnotationPresent(org.junit.jupiter.api.Test.class, method);
        reduceToDefaultScopeIfAnnotationPresent(BeforeAll.class, method);
        reduceToDefaultScopeIfAnnotationPresent(BeforeEach.class, method);
        reduceToDefaultScopeIfAnnotationPresent(AfterEach.class, method);
        reduceToDefaultScopeIfAnnotationPresent(AfterAll.class, method);
        return visit;
    }

    private void reduceToDefaultScopeIfAnnotationPresent(Class<? extends Annotation> annotationClass, MethodDeclaration n) {
        n.getAnnotationByClass(annotationClass).ifPresent(doNotCare -> {
            n.removeModifier(Modifier.PUBLIC);
        });
    }
}
