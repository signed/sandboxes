import ast
from unittest import TestCase
from printer import Printer
from printer import Paper
from drivers import MethodDefinitionPrinterDriver


class TestPrinter(TestCase):

    def setUp(self):
        self.paper = Paper()
        self.printer = Printer()
        self.printer.insert(self.paper)

    def test_addIndentationAtTheStartOfALineAsLongAsItIsNotRemoved(self):
        driver = MethodDefinitionPrinterDriver(self.printer)
        tree = ast.parse(open("samples/method.py", 'r').read())
        driver.visit(tree)

        self.assertEqual(self.paper.text, 'public Object basic_method( Object one, Object two){\n}')