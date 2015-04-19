package java8.exercises.chapter_01;

import static java.util.Comparator.comparing;
import static java8.exercises.chapter_01.FileTypeSafeMatcher.fileNamed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class Exercise_04 {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void chainComparators() throws Exception {
        folder.newFile("a");
        folder.newFile("z");
        folder.newFolder("1");
        folder.newFolder("2");

        File[] files = folder.getRoot().listFiles();
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

        assertThat(files, arrayContaining(fileNamed("1"), fileNamed("2"), fileNamed("a"), fileNamed("z")));
    }

}
