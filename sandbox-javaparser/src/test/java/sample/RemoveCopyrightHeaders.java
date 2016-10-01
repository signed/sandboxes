package sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

public class RemoveCopyrightHeaders {
    private final Path pathToSampleJavaFile = Paths.get("src/main/java/sample/JavaDocOnClass.java");

    @Test
    public void javaParser() throws Exception {
        removeCopyrightNoticeFrom(pathToSampleJavaFile);
    }

    @Test
    public void remove() throws Exception {
        removeCopyrightNoticeInJavaFilesForProjectAt(Paths.get(""));
    }

    private void removeCopyrightNoticeInJavaFilesForProjectAt(Path projectDirectory) throws IOException {
        log("searching for java files under " + projectDirectory);
        FileFinder javaFiles = new FileFinder("*.java");
        Files.walkFileTree(projectDirectory, javaFiles);
        log("found " + javaFiles.found.size() + " java file");
        javaFiles.found.parallelStream().forEach(this::removeCopyrightNoticeFrom);
    }

    private void log(String message) {
        System.out.println(message);
    }

    private void removeCopyrightNoticeFrom(Path javaSourceFile) {
        CompilationUnit cu = parseAsCompilationUnit(javaSourceFile);
        CopyrightNoticeScanner scanner = new CopyrightNoticeScanner();
        scanner.visit(cu, null);
        if (scanner.copyrightNoticesLocations.isEmpty()) {
            return;
        }
        if (scanner.copyrightNoticesLocations.size() > 1) {
            log("Skipping file with multiple copy right notices. Has multiple copy right notices: " + javaSourceFile);
            return;
        }

        Range location = scanner.copyrightNoticesLocations.get(0);

        if (location.begin.line != 1 | location.begin.column != 1) {
            log("Skipping file where copyright notice is not at the start of the file: " + javaSourceFile);
            return;
        }

        List<String> allLines = readAllLinesIn(javaSourceFile);
        List<String> copyrightNotice = extractLines(allLines, copyrightNotice(location));

        String lastCopyrightLine = copyrightNotice.get(copyrightNotice.size() - 1);

        if (lastCopyrightLine.length() != location.end.column -1) {
            log("Skipping file where last line of copyright notice seems not to span the entire line: " + javaSourceFile);
            return;
        }


        List<String> sourceLinesWithoutCopyrightNotice = extractLines(allLines, copyrightNotice(location).negate());

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


    public static class FileFinder extends SimpleFileVisitor<Path> {
        private PathMatcher matcher;
        public ArrayList<Path> found = new ArrayList<>();

        public FileFinder(String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path name = file.getFileName();

            if (matcher.matches(name)) {
                found.add(file);
            }

            return FileVisitResult.CONTINUE;
        }
    }

    private static class CopyrightNoticeScanner extends ModifierVisitorAdapter<Void> {
        public final List<Range> copyrightNoticesLocations = new ArrayList<>();

        @Override
        public Node visit(BlockComment n, Void arg) {
            if (n.getContent().toLowerCase().contains("Copyright".toLowerCase())) {
                copyrightNoticesLocations.add(n.getRange());
                return null;
            }
            return n;
        }
    }
}
