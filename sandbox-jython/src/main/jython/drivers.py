import ast


class MotorHome(object):
    printers = {}

    def __getitem__(self, item):
        return self.printers.get(item, VisitChildren())

    def __setitem__(self, key, item):
        self.printers[key] = item


class VisitChildren(ast.NodeVisitor):
    pass


class ModulePrinterDriver(ast.NodeVisitor):
    pass


class MethodDefinitionPrinterDriver(ast.NodeVisitor):
    def __init__(self, printer):
        self.printer = printer

    def visit_FunctionDef(self, function_definition):
        self.printer.write('public Object ' + function_definition.name + '( ')
        arguments_in_java = ''
        for argument in function_definition.args.args:
            if argument.id == 'self' :
                continue
            arguments_in_java += 'Object ' + argument.id+ ', '

        self.printer.write(arguments_in_java[:-2])
        self.printer.write('){\n')
        self.printer.write('}')
