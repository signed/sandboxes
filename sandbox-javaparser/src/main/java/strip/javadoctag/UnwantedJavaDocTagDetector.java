package strip.javadoctag;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import strip.Detector;

import javax.swing.text.html.Option;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public class UnwantedJavaDocTagDetector implements Detector {
    private Consumer<String> logger;
    private final String tagName;

    public UnwantedJavaDocTagDetector(String tagName, Consumer<String> logger) {
        this.tagName = tagName;
        this.logger = logger;
    }

    @Override
    public Optional<Range> findCodeToRemoveIn(CompilationUnit compilationUnit, Path javaSourceFile) {
        OneLineJavaDocTagScanner scanner = new OneLineJavaDocTagScanner(tagName);
        scanner.visit(compilationUnit, null);

        if (scanner.ranges.isEmpty()) {
            return Optional.empty();
        }
        if (scanner.ranges.size() > 1) {
            logger.accept("there is more than one " + tagName + " in " + javaSourceFile);
            return Optional.empty();
        }
        return Optional.of(scanner.ranges.get(0));
    }
}
