from unittest import TestCase
import ast
from printer import PrinterStorage
from printer import VisitChildren


class TestPrinterStorage(TestCase):
    def setUp(self):
        self.storage = PrinterStorage()

    def test_returnDefault(self):
        self.assertEqual(type(self.storage[ast.Module]), VisitChildren)