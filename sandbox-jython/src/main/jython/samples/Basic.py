import ast

class FirstParser(ast.NodeVisitor):

    def __init__(self):
        pass

    def continue2(self, stmt):
        '''Helper: parse a node's children'''
        super(FirstParser, self).generic_visit(stmt)

    def parse(self, code):
        '''Parse text into a tree and walk the result'''
        tree = ast.parse(code)
        self.visit(tree)

    def visit_Import(self, stmt_import):
        # retrieve the name from the returned object
        # normally, there is just a single alias
        for alias in stmt_import.names:
            print 'import name "%s"' % alias.name
            print 'import object %s' % alias

        self.continue2(stmt_import)

    def visit_BinOp(self, stmt_binop):
        print 'expression: '
        for child in ast.iter_fields(stmt_binop):
            print '  child %s ' % str(child)

        self.continue2(stmt_binop)

parser = FirstParser()
parser.parse('import foo')
parser.parse('a = b + 5')