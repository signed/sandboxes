__author__ = 'signed'

import ast


class ToJavaCodeNodeVisitor(ast.NodeVisitor):

    def __init__(self):
        pass

    def visit_Import(self, stmt_import):
        # retrieve the name from the returned object
        # normally, there is just a single alias
        for alias in stmt_import.names:
            print 'import name "%s"' % alias.name
            print 'import object %s' % str(alias)

        # allow parser to continue to parse the statement's children
        super(ToJavaCodeNodeVisitor, self).generic_visit(stmt_import)

    def visit_BinOp(self, stmt_binop):
        for child in ast.iter_fields(stmt_binop):
            print 'child %s' % str(child)



    def generic_visit(self, node):
        print node.__class__.__name__
        super(ToJavaCodeNodeVisitor, self).generic_visit(node)


source = open("HelloWorld.py", 'r').read()

tree = ast.parse(source)

ToJavaCodeNodeVisitor().visit(tree)

