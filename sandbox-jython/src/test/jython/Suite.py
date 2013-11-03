import unittest

import test_MotorHome
import test_Printer

loader = unittest.TestLoader()
printer_suite = loader.loadTestsFromModule(test_Printer)
motor_home_suite = loader.loadTestsFromModule(test_MotorHome)

global_suite = unittest.TestSuite([printer_suite, motor_home_suite])

unittest.TextTestRunner(verbosity=1).run(global_suite)