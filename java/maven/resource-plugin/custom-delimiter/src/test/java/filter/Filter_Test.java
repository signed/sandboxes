package filter;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class Filter_Test {
    @Test
    public void replaceThePattern() throws Exception {
        assertThat(filteredTestFile(), containsString("Hallo Thomas!"));
    }

    @Test
    public void doNotApplyDefaultFilterPattern() throws Exception {
        assertThat(filteredTestFile(), containsString("${are}"));
    }

    private String filteredTestFile() throws IOException {
        URL resource = Filter_Test.class.getResource("/filter.test");
        byte[] raw = Files.readAllBytes(Paths.get(resource.getPath()));
        return new String(raw, Charset.forName("UTF-8"));
    }
}