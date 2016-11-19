package clients;

import static java.lang.System.getProperty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Assume;
import org.junit.Test;

public class DockerTest {

    @Test
    public void name() throws Exception {
        Assume.assumeThat(getProperty("os.name"), equalTo("Linux"));
        assertThat(new Docker().dockerIp(), equalTo("localhost"));
    }
}