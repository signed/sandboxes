package java8.chapter_01.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static java.nio.file.Files.createFile;
import static org.assertj.core.api.Assertions.assertThat;

class Exercise_03 {

    @TempDir
    private Path directory;

    @Test
    void findFileWithExtension() throws Exception {

        createFile(directory.resolve("ignore.txt"));
        createFile(directory.resolve("file.xml"));

        String suffix = ".xml";
        File directory = this.directory.toFile();
        String[] matchingFiles = listFilesIn(directory, suffix);

        assertThat(matchingFiles).satisfiesExactly(item1 -> assertThat(item1).isEqualTo("file.xml"));
    }

    private String[] listFilesIn(File directory, String suffix) {
        return directory.list((dir, name) -> name.endsWith(suffix));
    }
}
