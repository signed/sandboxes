package strip.javadoctrim;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import strip.Detector;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class JavadocToTrimDetector implements Detector {
    private final Consumer<String> logger;

    public JavadocToTrimDetector(Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public List<Range> findCodeToRemoveIn(CompilationUnit compilationUnit, Path javaSourceFile) {
        JavadocToTrimScanner scanner = new JavadocToTrimScanner(logger);
        scanner.visit(compilationUnit, null);
        return Collections.unmodifiableList(scanner.ranges);
    }
}
