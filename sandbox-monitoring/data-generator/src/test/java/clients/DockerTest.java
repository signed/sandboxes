package clients;

import org.junit.Assume;
import org.junit.Test;

import static java.lang.System.getProperty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DockerTest {

    @Test
    public void name() throws Exception {
        Assume.assumeThat(getProperty("os.name"), equalTo("linux"));
        assertThat(new Docker().dockerIp(), equalTo("localhost"));
    }
}