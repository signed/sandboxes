package strip.javadoctag;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import strip.Detector;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class UnwantedJavaDocTagDetector implements Detector {
    private Consumer<String> logger;
    private final String tagName;

    public UnwantedJavaDocTagDetector(String tagName, Consumer<String> logger) {
        this.tagName = tagName;
        this.logger = logger;
    }

    @Override
    public List<Range> findCodeToRemoveIn(CompilationUnit compilationUnit, Path javaSourceFile) {
        OneLineJavaDocTagScanner scanner = new OneLineJavaDocTagScanner(tagName);
        scanner.visit(compilationUnit, null);
        return Collections.unmodifiableList(scanner.ranges);
    }
}
