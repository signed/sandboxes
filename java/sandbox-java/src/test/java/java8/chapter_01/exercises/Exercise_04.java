package java8.chapter_01.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

import static java.nio.file.Files.createFile;
import static org.assertj.core.api.Assertions.assertThat;

public class Exercise_04 {

    @TempDir
    private Path directory;


    @Test
    public void chainComparators() throws Exception {
        createFile(directory.resolve("a"));
        createFile(directory.resolve("z"));
        createFile(directory.resolve("1"));
        createFile(directory.resolve("2"));


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
        Arrays.sort(files, compareByType.thenComparing(File::getName));

        assertThat(files).satisfiesExactly(
                item1 -> assertThat(item1).hasFileName("1"),
                item2 -> assertThat(item2).hasFileName("2"),
                item3 -> assertThat(item3).hasFileName("a"),
                item4 -> assertThat(item4).hasFileName("z")
        );
    }

}
