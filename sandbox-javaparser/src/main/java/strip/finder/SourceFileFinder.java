package strip.finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SourceFileFinder {

    private Consumer<String> logger;

    public SourceFileFinder(Consumer<String> logger) {
        this.logger = logger;
    }

    public ArrayList<Path> javaFilesInProjectAt(Path projectDirectory) throws IOException {
        logger.accept("searching for java files under " + projectDirectory);
        FileFinder javaFiles = new FileFinder("*.java");
        Files.walkFileTree(projectDirectory, javaFiles);
        logger.accept("found " + javaFiles.found.size() + " java file");
        return javaFiles.found;
    }
}
