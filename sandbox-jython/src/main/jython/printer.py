import ast


class PrinterStorage(object):
    printers = {}

    def __getitem__(self, item):
        return self.printers.get(item, VisitChildren())

    def __setitem__(self, key, item):
        self.printers[key] = item


class VisitChildren(ast.NodeVisitor):
    pass


class ModulePrinterDriver(ast.NodeVisitor):
    pass