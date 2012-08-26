package newinputoutput;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Path_Test {

    @Test
    public void obtainAPathToTheUserHomeDirectory() throws Exception {
        Path userHome = Paths.get(System.getProperty("user.home"));
        assertThat(Files.exists(userHome), is(true));
    }
}
