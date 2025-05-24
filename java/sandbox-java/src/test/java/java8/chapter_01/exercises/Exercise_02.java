package java8.chapter_01.exercises;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;

public class Exercise_02 {

    @Rule
    public final TemporaryFolder directory = new TemporaryFolder();

    @Test
    public void listAllSubdirectories() throws Exception {
        directory.newFile("please ignore");
        directory.newFolder("hello");
        File[] files = directory.getRoot().listFiles(File::isDirectory);
        assertThat(files, Matchers.arrayContaining(FileTypeSafeMatcher.fileNamed("hello")));
    }

}
