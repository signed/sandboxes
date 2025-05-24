package java8.chapter_01.exercises;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.createFile;
import static org.hamcrest.MatcherAssert.assertThat;

class Exercise_02 {

    @TempDir
    private Path directory;

    @Test
    void listAllSubdirectories() throws Exception {
        createFile(directory.resolve("please ignore"));
        createDirectory(directory.resolve("hello"));

        File[] files = Files.list(directory).filter(Files::isDirectory).map(Path::toFile).toArray(File[]::new);
        assertThat(files, Matchers.arrayContaining(FileTypeSafeMatcher.fileNamed("hello")));
    }

}
