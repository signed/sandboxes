class Printer(object):
    paper = None
    indentationLevel = 0

    def insert(self, paper):
        self.paper = paper

    def write(self, text):
        if self.paper.isAtStartOfLine():
            for _ in range(self.indentationLevel):
                self.paper.append('    ')
        self.paper.append(text)

    def newline(self):
        self.paper.append('\n')

    def increaseIndentation(self):
        self.indentationLevel += 1


class Paper(object):
    text = ''

    def isAtStartOfLine(self):
        return not self.text or self.text[-1] == '\n'

    def append(self, text):
        self.text += text