package sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import org.junit.Test;

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

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

public class RemoveCopyrightHeaders {
    Path pathToSample = Paths.get("src/main/java/sample/JavaDocOnClass.java");

    @Test
    public void javaParser() throws Exception {
        CompilationUnit cu = JavaParser.parse(pathToSample.toFile().getAbsoluteFile());
        ModifierVisitorAdapter<Void> modifier = new ModifierVisitorAdapter<Void>() {

            @Override
            public Node visit(BlockComment n, Void arg) {
                if (n.getContent().toLowerCase().contains("Copyright".toLowerCase())) {
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
        };
        modifier.visit(cu, null);
        byte[] bytes = cu.toString().getBytes(Charset.forName("UTF-8"));
        Files.write(pathToSample, bytes, WRITE, TRUNCATE_EXISTING);
    }

    @Test
    public void spoon() throws Exception {

        FileFinder javaFiles = new FileFinder("*.java");
        Files.walkFileTree(Paths.get(""), javaFiles);

        System.out.println(javaFiles.foundPaths);
        Files.readAllLines(javaFiles.foundPaths.get(0));
    }


    public static class FileFinder extends SimpleFileVisitor<Path> {
        private PathMatcher matcher;
        public ArrayList<Path> foundPaths = new ArrayList<>();

        public FileFinder(String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path name = file.getFileName();

            if (matcher.matches(name)) {
                foundPaths.add(file);
            }

            return FileVisitResult.CONTINUE;
        }
    }

}
