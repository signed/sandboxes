package strip;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface Detector {
    List<Range> findCodeToRemoveIn(CompilationUnit compilationUnit, Path javaSourceFile);
}
