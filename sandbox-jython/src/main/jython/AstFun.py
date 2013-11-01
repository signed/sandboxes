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

    # def visit_BinOp(self, stmt_binop):
    #     for child in ast.iter_fields(stmt_binop):
    #         print 'child %s' % str(child)

    def visit_Module(self, module):
        def nothing(node):
            print 'do nothing'
            return

        def print_node(node):
            print node

        process = {ast.ClassDef: print_node}
        for element in module.body:
            process.get(type(element), nothing)(element)
            super(ToJavaCodeNodeVisitor, self).generic_visit(element)

    def plain_return(self, string):
        return string

    def call_generic_visit_in_super_class(self, node):
        super(ToJavaCodeNodeVisitor, self).generic_visit(node)

    def generic_visit(self, node):
        # print node.__class__.__name__
        super(ToJavaCodeNodeVisitor, self).generic_visit(node)


source = open("HelloWorld.py", 'r').read()

tree = ast.parse(source)

ToJavaCodeNodeVisitor().visit(tree)

