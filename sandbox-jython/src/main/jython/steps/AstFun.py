__author__ = 'signed'

import ast
import sys


class ToJavaCodeNodeVisitor(ast.NodeVisitor):
    def __init__(self):
        pass

    def generic_visit(self, node):
        super(ToJavaCodeNodeVisitor, self).generic_visit(node)

    def nothing(self, node):
        pass

    def print_node(self, node):
        print 'called from module' + node.__class__.__name__
        self.generic_visit(node)

    def visit_Module(self, module):
        process = {ast.Import: self.visit, ast.ClassDef: self.visit}
        for element in module.body:
            process.get(type(element), self.nothing)(element)

    def visit_Import(self, stmt_import):
        for name in stmt_import.names:
            sys.stdout.write('import ' + name.name + ";\n")

    def visit_ClassDef(self, class_def):
        class_name = class_def.name
        print 'public class ' + class_name + ' {\n'
        for element in class_def.body:
            self.visit(element)
        print '\n}'

    def visit_FunctionDef(self, function_definition):
        print 'public Object ' + function_definition.name + '( ' + 'args missing' + '){'
        for element in function_definition.body:
            print element
        print '\n}'


source = open("HelloWorld.py", 'r').read()
tree = ast.parse(source)
ToJavaCodeNodeVisitor().visit(tree)

