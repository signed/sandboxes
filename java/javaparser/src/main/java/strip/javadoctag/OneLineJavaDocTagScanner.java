package strip.javadoctag;

import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class OneLineJavaDocTagScanner extends ModifierVisitorAdapter<Void> {
    private final String tagName;
    public List<Range> ranges;

    public OneLineJavaDocTagScanner(String tagName) {
        this.tagName = tagName;
        ranges = new ArrayList<>();
    }

    @Override
    public Node visit(JavadocComment n, Void arg) {
        int beginLine = n.getRange().begin.line;
        String[] lines = n.getContent().split("\n");
        for (String line : lines) {
            if (line.toLowerCase().contains("@" + tagName)) {
                ranges.add(new Range(new Position(beginLine, 1), new Position(beginLine, line.length() + 1)));
            }
            ++beginLine;
        }
        return n;
    }
}
