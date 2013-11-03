class Printer(object):
    paper = None

    def insert(self, paper):
        self.paper = paper

    def write(self, text):
        self.paper.append(text)


class Paper(object):
    text = ''

    def append(self, text):
        self.text += text