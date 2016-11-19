package clients;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class DockerTest {

    @Test
    public void name() throws Exception {
        assertThat(new Docker().dockerIp(), equalTo("localhost"));
    }
}