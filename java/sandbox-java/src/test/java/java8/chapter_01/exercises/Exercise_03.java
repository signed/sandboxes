package java8.chapter_01.exercises;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;

class Exercise_03 {

    @TempDir
    private Path directory;


    @Test
    void findFileWithExtension() throws Exception {

        Files.createFile(
                directory.resolve("ignore.txt")
        );
        Files.createFile(
                directory.resolve("file.xml")
        );

        String suffix = ".xml";
        File directory = this.directory.toFile();
        String[] matchingFiles = listFilesIn(directory, suffix);

        assertThat(matchingFiles, Matchers.arrayContaining(Matchers.equalTo("file.xml")));
    }

    private String[] listFilesIn(File directory, String suffix) {
        return directory.list((dir, name) -> name.endsWith(suffix));
    }
}
