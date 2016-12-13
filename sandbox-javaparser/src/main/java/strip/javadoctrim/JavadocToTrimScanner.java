package strip.javadoctrim;

import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavadocToTrimScanner extends ModifierVisitorAdapter<Void> {

    public final List<Range> ranges = new ArrayList<>();
    private final Consumer<String> logger;

    public JavadocToTrimScanner(Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public Node visit(JavadocComment n, Void arg) {
        String[] javadocLines = n.getContent().split("\n");
        List<String> cleanedUpLines = Arrays.stream(javadocLines).map(line -> line.replaceFirst("^\\s*\\*", "").trim()).collect(Collectors.toList());
        if (allLinesEmpty(cleanedUpLines)) {
            ranges.add(n.getRange());
            return n;
        }

        int emptyLinesAtStartOfJavaDoc = numberOfEmptyLineAtStart(cleanedUpLines.stream());
        if (emptyLinesAtStartOfJavaDoc > 1) {
            int startLine = n.getRange().begin.line + 1; //keep the first line
            int endLine = n.getRange().begin.line + emptyLinesAtStartOfJavaDoc - 1;
            int lastLineLengthIncludingNewLine = javadocLines[emptyLinesAtStartOfJavaDoc - 1].length() + 1;
            ranges.add(new Range(new Position(startLine, 1), new Position(endLine, lastLineLengthIncludingNewLine)));
        }

        int emptyLinesAtEndOfJavaDoc = numberOfEmptyLineAtStart(copyAndReverse(cleanedUpLines).stream());
        if (emptyLinesAtEndOfJavaDoc > 1) {
            int startLine = n.getRange().end.line - emptyLinesAtEndOfJavaDoc + 1;
            int endLine = n.getRange().end.line - 1;
            int lastLineLengthIncludingNewLine = javadocLines[javadocLines.length - emptyLinesAtEndOfJavaDoc].length() + 1;
            ranges.add(new Range(new Position(startLine, 1), new Position(endLine, lastLineLengthIncludingNewLine)));
        }
        return n;
    }

    private List<String> copyAndReverse(List<String> cleanedUpLines) {
        List<String> reveresed = new ArrayList<>(cleanedUpLines);
        Collections.reverse(reveresed);
        return reveresed;
    }

    private int numberOfEmptyLineAtStart(Stream<String> lines) {
        return (int) StreamEx.of(lines).map(String::isEmpty).takeWhile(isEmpty -> isEmpty).count();
    }

    private Boolean allLinesEmpty(List<String> cleanedUpLines) {
        return cleanedUpLines.stream().map(String::isEmpty).reduce(Boolean.TRUE, (aBoolean, aBoolean2) -> aBoolean && aBoolean2);
    }
}
