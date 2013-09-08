__author__ = 'signed'

import ast


class ToJavaCodeNodeVisitor(ast.NodeVisitor):

    def __init__(self):
        pass

    def generic_visit(self, node):
        print node.__class__.__name__
        super(ToJavaCodeNodeVisitor, self).generic_visit(node)


source = open("HelloWorld.py", 'r').read()

tree = ast.parse(source)

ToJavaCodeNodeVisitor().visit(tree)

