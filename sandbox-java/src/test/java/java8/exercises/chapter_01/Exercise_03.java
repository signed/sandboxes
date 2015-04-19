package java8.exercises.chapter_01;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class Exercise_03 {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void findFileWithExtension() throws Exception {
        folder.newFile("ignore.txt");
        folder.newFile("file.xml");
        String suffix = ".xml";
        File directory = folder.getRoot();
        String[] matchingFiles = listFilesIn(directory, suffix);

        assertThat(matchingFiles, Matchers.arrayContaining(Matchers.equalTo("file.xml")));
    }

    private String[] listFilesIn(File directory, String suffix) {
        return directory.list((dir, name) -> name.endsWith(suffix));
    }
}
