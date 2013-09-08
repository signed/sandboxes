__author__ = 'signed'


import ast
import decimal

source = "(Decimal('11.66985'), Decimal('1e-8')," \
         "(1,), (1,2,3), 1.2, [1,2,3], {1:2})"

tree = ast.parse(source, mode='eval')

# using the NodeTransformer, you can also modify the nodes in the tree,
# however in this example NodeVisitor could do as we are raising exceptions
# only.
class Transformer(ast.NodeTransformer):
    ALLOWED_NAMES = set(['Decimal', 'None', 'False', 'True'])
    ALLOWED_NODE_TYPES = set([
        'Expression', # a top node for an expression
        'Tuple',      # makes a tuple
        'Call',       # a function call (hint, Decimal())
        'Name',       # an identifier...
        'Load',       # loads a value of a variable with given identifier
        'Str',        # a string literal

        'Num',        # allow numbers too
        'List',       # and list literals
        'Dict',       # and dicts...
    ])

    def visit_Name(self, node):
        if not node.id in self.ALLOWED_NAMES:
            raise RuntimeError("Name access to %s is not allowed" % node.id)

        # traverse to child nodes
        return self.generic_visit(node)

    def generic_visit(self, node):
        nodetype = type(node).__name__
        if nodetype not in self.ALLOWED_NODE_TYPES:
            raise RuntimeError("Invalid expression: %s not allowed" % nodetype)

        return ast.NodeTransformer.generic_visit(self, node)

transformer = Transformer()

# raises RuntimeError on invalid code
transformer.visit(tree)

# compile the ast into a code object
clause = compile(tree, '<AST>', 'eval')

# make the globals contain only the Decimal class,
# and eval the compiled object
result = eval(clause, dict(Decimal=decimal.Decimal))

print(result)