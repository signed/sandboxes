package clients;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DockerTest {

    @Test
    public void name() throws Exception {

        assertThat(new Docker().dockerIp(), equalTo("localhost"));
    }
}