package strip;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Path;
import java.util.Optional;

public interface Detector {
    Optional<Range> findCodeToRemoveIn(CompilationUnit compilationUnit, Path javaSourceFile);
}
