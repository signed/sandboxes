package java8.chapter_01.exercises;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

import static java.util.Comparator.comparing;
import static org.hamcrest.MatcherAssert.assertThat;

public class Exercise_04 {

    @TempDir
    private Path directory;


    @Test
    public void chainComparators() throws Exception {
        Files.createFile(
                directory.resolve("a")
        );
        Files.createFile(
                directory.resolve("z")
        );
        Files.createFile(
                directory.resolve("1")
        );
        Files.createFile(
                directory.resolve("2")
        );


        File[] files = Files.list(directory).map(Path::toFile).toArray(File[]::new);
        Comparator<File> compareByType = (o1, o2) -> {
            boolean o1IsDirectory = o1.isDirectory();
            boolean o2IsDirectory = o2.isDirectory();

            if (o1IsDirectory == o2IsDirectory) {
                return 0;
            }

            if (o1IsDirectory) {
                return -1;
            }
            return 1;
        };
        Arrays.sort(files, compareByType.thenComparing(comparing(File::getName)));

        assertThat(files, Matchers.arrayContaining(FileTypeSafeMatcher.fileNamed("1"), FileTypeSafeMatcher.fileNamed("2"), FileTypeSafeMatcher.fileNamed("a"), FileTypeSafeMatcher.fileNamed("z")));
    }

}
