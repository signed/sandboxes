__author__ = 'signed'

import tokenize

print 'running...'

source = open("HelloWorld.py", 'r').read()

for t in tokenize.generate_tokens(iter([source]).next):
    tokenize.printtoken(*t)
