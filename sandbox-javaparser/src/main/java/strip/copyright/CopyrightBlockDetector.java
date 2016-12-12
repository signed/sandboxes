package strip.copyright;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import strip.copyright.CopyrightNoticeScanner;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public class CopyrightBlockDetector implements strip.Detector {

    private Consumer<String> logger;

    public CopyrightBlockDetector(Consumer<String> logger){
        this.logger = logger;
    }

    @Override
    public Optional<Range> findCodeToRemoveIn(CompilationUnit compilationUnit, Path javaSourceFile) {
        CopyrightNoticeScanner scanner = new CopyrightNoticeScanner();
        scanner.visit(compilationUnit, null);
        if (scanner.copyrightNoticesLocations.isEmpty()) {
            return Optional.empty();
        }
        if (scanner.copyrightNoticesLocations.size() > 1) {
            logger.accept("Skipping file with multiple copy right notices. Has multiple copy right notices: " + javaSourceFile);
            return Optional.empty();
        }

        Range location = scanner.copyrightNoticesLocations.get(0);

        if (location.begin.line != 1 | location.begin.column != 1) {
            logger.accept("Skipping file where copyright notice is not at the start of the file: " + javaSourceFile);
            return Optional.empty();
        }
        return Optional.of(location);
    }
}
