package strip.emptyjavadoc;

import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
        return n;
    }

    private Boolean allLinesEmpty(List<String> cleanedUpLines) {
        return cleanedUpLines.stream().map(String::isEmpty).reduce(Boolean.TRUE, (aBoolean, aBoolean2) -> aBoolean && aBoolean2);
    }
}
