package charstreams;

import com.google.common.io.CharStreams;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InputStreamAsString {

    @Test
    public void readAnEntireInputStreamAsString() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("the content".getBytes());

        assertThat(CharStreams.toString(new InputStreamReader(inputStream, "UTF-8")), is("the content"));
    }
}
