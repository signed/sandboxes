package newinputoutput;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Path_Test {

    @Test
    void obtainAPathToTheUserHomeDirectory() throws Exception {
        Path userHome = Paths.get(System.getProperty("user.home"));
        assertThat(Files.exists(userHome), is(true));
    }
}
