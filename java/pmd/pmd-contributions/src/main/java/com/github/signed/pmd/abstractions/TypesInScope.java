package com.github.signed.pmd.abstractions;

import net.sourceforge.pmd.lang.java.ast.ASTImportDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTName;

import java.util.List;

public class TypesInScope {

    private final List<ASTImportDeclaration> imports;

    public TypesInScope(List<ASTImportDeclaration> imports) {
        this.imports = imports;
    }

    public String typeByClassName(String className) {
        return imports.get(0).getFirstChildOfType(ASTName.class).getImage();
    }
}
