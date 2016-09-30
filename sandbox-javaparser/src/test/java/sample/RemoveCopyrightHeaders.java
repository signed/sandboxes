package sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
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
import java.util.Optional;

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
        log("searching for java files under "+ projectDirectory);
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
        CopyrightNoticeRemover remover = new CopyrightNoticeRemover();
        remover.visit(cu, null);
        remover.removedCopyrightNotice.ifPresent(ignored -> {
            byte[] bytes = cu.toString().getBytes(Charset.forName("UTF-8"));
            write(javaSourceFile, bytes);
        });
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

    private static class CopyrightNoticeRemover extends ModifierVisitorAdapter<Void> {
        public Optional<Boolean> removedCopyrightNotice = Optional.empty();

        @Override
        public Node visit(BlockComment n, Void arg) {
            if (n.getContent().toLowerCase().contains("Copyright".toLowerCase())) {
                removedCopyrightNotice = Optional.of(Boolean.TRUE);
                return null;
            }
            return n;
        }

        @Override
        public Node visit(VariableDeclarator declarator, Void arg) {
            if (declarator.getId().getName().equals("a") && declarator.getInit().toString().equals("20")) {
                return null;
            }
            return declarator;
        }
    }
}
