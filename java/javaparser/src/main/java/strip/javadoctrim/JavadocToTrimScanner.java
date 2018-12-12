package strip.javadoctrim;

import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavadocToTrimScanner extends ModifierVisitor<Void> {

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
            ranges.add(n.getRange().orElseThrow());
            return n;
        }

        int emptyLinesAtStartOfJavaDoc = numberOfEmptyLineAtStart(cleanedUpLines.stream());
        if (emptyLinesAtStartOfJavaDoc > 1) {
            int startLine = n.getRange().orElseThrow().begin.line + 1; //keep the first line
            int endLine = n.getRange().orElseThrow().begin.line + emptyLinesAtStartOfJavaDoc - 1;
            int lastLineLengthIncludingNewLine = javadocLines[emptyLinesAtStartOfJavaDoc - 1].length() + 1;
            ranges.add(new Range(new Position(startLine, 1), new Position(endLine, lastLineLengthIncludingNewLine)));
        }

        ConsecutiveEmptyLineBuilder builder = new ConsecutiveEmptyLineBuilder(javadocLines, n.getRange().orElseThrow().begin.line);
        for (int currentLine = 0; currentLine < cleanedUpLines.size(); currentLine++) {
            if (cleanedUpLines.get(currentLine).isEmpty()) {
                builder.emptyLineAt(currentLine);
            } else {
                builder.maybeCompressableRange().ifPresent(ranges::add);
                builder.reset();
            }
        }

        int emptyLinesAtEndOfJavaDoc = numberOfEmptyLineAtStart(copyAndReverse(cleanedUpLines).stream());
        if (emptyLinesAtEndOfJavaDoc > 1) {
            int startLine = n.getRange().orElseThrow().end.line - emptyLinesAtEndOfJavaDoc + 1;
            int endLine = n.getRange().orElseThrow().end.line - 1;
            int lastLineLengthIncludingNewLine = javadocLines[javadocLines.length - emptyLinesAtEndOfJavaDoc].length() + 1;
            ranges.add(new Range(new Position(startLine, 1), new Position(endLine, lastLineLengthIncludingNewLine)));
        }
        return n;
    }

    public static class ConsecutiveEmptyLineBuilder {
        private final String[] javadocLines;
        private int offset;
        private int firstEmptyLine;
        private int lastEmptyLine;

        public ConsecutiveEmptyLineBuilder(String[] javadocLines, int offset) {
            this.javadocLines = javadocLines;
            this.offset = offset;
            reset();
        }

        public ConsecutiveEmptyLineBuilder emptyLineAt(int line) {
            if (firstEmptyLine == -1) {
                firstEmptyLine = line;
            }
            lastEmptyLine = line;
            return this;
        }

        public void reset() {
            firstEmptyLine = -1;
            lastEmptyLine = javadocLines.length;
        }

        public Optional<Range> maybeCompressableRange() {
            if (firstEmptyLine == -1 || firstEmptyLine == 0 || lastEmptyLine == javadocLines.length) {
                return Optional.empty();
            }
            if (lastEmptyLine - firstEmptyLine < 1) {
                return Optional.empty();
            }
            Position begin = new Position(offset + firstEmptyLine, 0);
            int keepLastEmptyLine = lastEmptyLine - 1;
            Position end = new Position(offset + keepLastEmptyLine, javadocLines[keepLastEmptyLine].length() + 1);
            return Optional.of(new Range(begin, end));
        }
    }

    private List<String> copyAndReverse(List<String> cleanedUpLines) {
        List<String> reversed = new ArrayList<>(cleanedUpLines);
        Collections.reverse(reversed);
        return reversed;
    }

    private int numberOfEmptyLineAtStart(Stream<String> lines) {
        return (int) StreamEx.of(lines).map(String::isEmpty).takeWhile(isEmpty -> isEmpty).count();
    }

    private Boolean allLinesEmpty(List<String> cleanedUpLines) {
        return cleanedUpLines.stream().map(String::isEmpty).reduce(Boolean.TRUE, (aBoolean, aBoolean2) -> aBoolean && aBoolean2);
    }
}
