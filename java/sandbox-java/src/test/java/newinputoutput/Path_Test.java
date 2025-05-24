package newinputoutput;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Path_Test {

    @Test
    void obtainAPathToTheUserHomeDirectory() throws Exception {
        Path userHome = Paths.get(System.getProperty("user.home"));
        assertThat(Files.exists(userHome)).isTrue();
    }
}
