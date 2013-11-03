import unittest, FirstTest, TestLists, jump.test_PrinterStorage

loader = unittest.TestLoader()
math_suite = loader.loadTestsFromModule(FirstTest)
lists_suite = loader.loadTestsFromModule(TestLists)
printer_storage_suit = loader.loadTestsFromModule(jump.test_PrinterStorage)

global_suite = unittest.TestSuite([math_suite, lists_suite, printer_storage_suit])

unittest.TextTestRunner(verbosity=2).run(global_suite)