package sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import strip.Detector;
import strip.finder.SourceFileFinder;
import strip.copyright.CopyrightBlockDetector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

public class RemoveCopyrightHeaders {

    private final CopyrightBlockDetector copyrightBlockDetector = new CopyrightBlockDetector(this::log);
    private final SourceFileFinder sourceFileFinder = new SourceFileFinder(this::log);
    private final Path pathToSampleJavaFile = Paths.get("src/main/java/sample/JavaDocOnClass.java");

    @Test
    public void javaParser() throws Exception {
        removeCopyrightNoticeFrom(pathToSampleJavaFile, copyrightBlockDetector);
    }

    @Test
    public void removeCopyRightHeader() throws Exception {
        sourceFileFinder.javaFilesInProjectAt(Paths.get(""))
                .parallelStream()
                .forEach((javaSourceFile) -> removeCopyrightNoticeFrom(javaSourceFile, copyrightBlockDetector));
    }

    private void removeCopyrightNoticeFrom(Path javaSourceFile, Detector detector) {
        CompilationUnit cu = parseAsCompilationUnit(javaSourceFile);
        Optional<Range> maybeToRemove = detector.findCodeToRemoveIn(cu, javaSourceFile);
        if (!maybeToRemove.isPresent()) {
            return;
        }
        removeRangeIn(maybeToRemove.get(), javaSourceFile);
    }

    private void removeRangeIn(Range locationToRemove, Path javaSourceFile) {
        List<String> allLines = readAllLinesIn(javaSourceFile);
        List<String> copyrightNotice = extractLines(allLines, copyrightNotice(locationToRemove));

        String lastCopyrightLine = copyrightNotice.get(copyrightNotice.size() - 1);

        if (lastCopyrightLine.length() != locationToRemove.end.column - 1) {
            log("Skipping file where last line of copyright notice seems not to span the entire line: " + javaSourceFile);
            return;
        }


        List<String> sourceLinesWithoutCopyrightNotice = extractLines(allLines, copyrightNotice(locationToRemove).negate());

        while (sourceLinesWithoutCopyrightNotice.get(0).trim().isEmpty()) {
            sourceLinesWithoutCopyrightNotice.remove(0);
        }

        byte[] bytes = sourceLinesWithoutCopyrightNotice.stream().collect(Collectors.joining("\n")).getBytes(utf_8);
        write(javaSourceFile, bytes);
    }

    private List<String> extractLines(List<String> allLines, IntPredicate lineSelector) {
        return IntStream.range(0, allLines.size())
                .filter(lineSelector)
                .mapToObj(allLines::get)
                .collect(Collectors.toList());
    }

    private IntPredicate copyrightNotice(Range location) {
        return line -> {
            int beginLineZeroBased = location.begin.line - 1;
            int endLineZeroBased = location.end.line - 1;
            return !(line < beginLineZeroBased | line > endLineZeroBased);
        };
    }

    private final Charset utf_8 = Charset.forName("UTF-8");

    private List<String> readAllLinesIn(Path javaSourceFile) {
        try {
            byte[] bytes = Files.readAllBytes(javaSourceFile);
            String javaSource = new String(bytes, utf_8);
            return Arrays.asList(javaSource.split("\n", -1));
        } catch (IOException e) {
            throw new RuntimeException("should never happen", e);
        }
    }

    private CompilationUnit parseAsCompilationUnit(Path javaSourceFile) {
        try {
            return JavaParser.parse(javaSourceFile.toAbsolutePath().toFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("should never happen", e);
        }
    }

    private Path write(Path javaSourceFile, byte[] bytes) {
        try {
            return Files.write(javaSourceFile, bytes, WRITE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("should never happen", e);
        }
    }

    private void log(String message) {
        System.out.println(message);
    }


}
