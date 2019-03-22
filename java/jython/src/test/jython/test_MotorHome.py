from unittest import TestCase
import ast
from drivers import MotorHome
from drivers import VisitChildren
from drivers import ModulePrinterDriver


class TestMotorHome(TestCase):
    def setUp(self):
        self.storage = MotorHome()

    def test_justVisitAllChildrenIfNoSpecialPrinterIsRegistered(self):
        self.assertEqual(type(self.storage[ast.Module]), VisitChildren)

    def test_useThePrinterThatIsRegisteredForTheAstClass(self):
        self.storage[ast.Module] = ModulePrinterDriver()
        self.assertEqual(type(self.storage[ast.Module]), ModulePrinterDriver)