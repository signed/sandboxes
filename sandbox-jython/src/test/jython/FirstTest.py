import math
import unittest


class FirstTest(unittest.TestCase):

    def test_Floor(self):
        self.assertEqual(1, math.floor(1.01))
        self.assertEqual(0, math.floor(0.5))
        self.assertEqual(-1, math.floor(-0.5))
        self.assertEqual(-2, math.floor(-1.1))

    def test_Ceil(self):
        self.assertEqual(2, math.ceil(1.01))
        self.assertEqual(1, math.ceil(0.5))
        self.assertEqual(0, math.ceil(-0.5))
        self.assertEqual(-1, math.ceil(-1.2))

    def test_Fail(self):
        self.assertEqual('one', 'one')

if __name__ == '__main__':
    unittest.main()