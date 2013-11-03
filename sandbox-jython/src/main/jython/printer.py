import ast


class PrinterStorage():
    def __getitem__(self, item):
        return VisitChildren()


class VisitChildren(ast.NodeVisitor):
    pass