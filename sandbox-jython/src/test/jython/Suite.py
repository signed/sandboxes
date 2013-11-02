import unittest, FirstTest, TestLists

loader = unittest.TestLoader()
math_suite = loader.loadTestsFromModule(FirstTest)
lists_suite = loader.loadTestsFromModule(TestLists)
global_suite = unittest.TestSuite([math_suite, lists_suite])

unittest.TextTestRunner().run(global_suite)