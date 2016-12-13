package strip.emptyjavadoc;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import strip.Detector;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public class EmptyJavadocDetector implements Detector {
    private final Consumer<String> logger;

    public EmptyJavadocDetector(Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public Optional<Range> findCodeToRemoveIn(CompilationUnit compilationUnit, Path javaSourceFile) {
        JavadocToTrimScanner scanner = new JavadocToTrimScanner(logger);
        scanner.visit(compilationUnit, null);
        if (scanner.ranges.size() == 1) {
            return Optional.of(scanner.ranges.get(0));
        }
        return Optional.empty();
    }
}
