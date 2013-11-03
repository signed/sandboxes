from unittest import TestCase
from printer import Printer
from printer import Paper


class TestPrinter(TestCase):
    def setUp(self):
        self.paper = Paper()
        self.printer = Printer()
        self.printer.insert(self.paper)

    def test_justVisitAllChildrenIfNoSpecialPrinterIsRegistered(self):
        self.printer.write('Some text')
        self.assertEqual(self.paper.text, 'Some text')

    def test_changeLine(self):
        self.printer.write('Some text')
        self.printer.newline()
        self.printer.write('more text')
        self.assertEqual(self.paper.text, 'Some text\nmore text')

    def test_addIndentationAtTheStartOfALine(self):
        self.printer.increaseIndentation()
        self.printer.write('Some text')
        self.assertEqual(self.paper.text, '    Some text')

    def test_addIndentationAtTheStartOfALineAsLongAsItIsNotRemoved(self):
        self.printer.increaseIndentation()
        self.printer.write('Some text')
        self.printer.newline()
        self.printer.write('more text')
        self.assertEqual(self.paper.text, '    Some text\n    more text')