__author__ = 'signed'


import tokenize, cStringIO

source = "{'test':'123','hehe':['hooray',0x10]}"
src = cStringIO.StringIO(source).readline

print 'running...'
result = tokenize.generate_tokens(src)

for t in tokenize.generate_tokens(iter([source]).next):
    tokenize.printtoken(*t)
