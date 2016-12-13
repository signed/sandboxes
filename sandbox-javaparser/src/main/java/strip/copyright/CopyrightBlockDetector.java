package strip.copyright;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CopyrightBlockDetector implements strip.Detector {

    private Consumer<String> logger;

    public CopyrightBlockDetector(Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public List<Range> findCodeToRemoveIn(CompilationUnit compilationUnit, Path javaSourceFile) {
        CopyrightNoticeScanner scanner = new CopyrightNoticeScanner();
        scanner.visit(compilationUnit, null);
        if (scanner.copyrightNoticesLocations.size() > 1) {
            logger.accept("Skipping file with multiple copy right notices. Has multiple copy right notices: " + javaSourceFile);
            return Collections.emptyList();
        }

        Range location = scanner.copyrightNoticesLocations.get(0);

        if (location.begin.line != 1 | location.begin.column != 1) {
            logger.accept("Skipping file where copyright notice is not at the start of the file: " + javaSourceFile);
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(scanner.copyrightNoticesLocations);
    }
}
