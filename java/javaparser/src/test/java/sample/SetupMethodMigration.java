package sample;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class SetupMethodMigration extends ModifierVisitor<Void> {
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
