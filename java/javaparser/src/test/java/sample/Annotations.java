package sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class Annotations {
    public static void replace(MethodDeclaration n, Class<? extends Annotation> junit4, Class<? extends Annotation> junit5) {
        Optional<AnnotationExpr> annotationByClass = n.getAnnotationByClass(junit4);
        annotationByClass.ifPresent(it -> {
            it.replace(JavaParser.parseAnnotation("@" + junit5.getSimpleName()));
        });
    }
}
