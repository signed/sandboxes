import unittest

import test_MotorHome
import test_Printer
import test_Driver

loader = unittest.TestLoader()
printer_suite = loader.loadTestsFromModule(test_Printer)
motor_home_suite = loader.loadTestsFromModule(test_MotorHome)
test_Driver_suite = loader.loadTestsFromModule(test_Driver)

global_suite = unittest.TestSuite([printer_suite, motor_home_suite, test_Driver_suite])

unittest.TextTestRunner(verbosity=1).run(global_suite)