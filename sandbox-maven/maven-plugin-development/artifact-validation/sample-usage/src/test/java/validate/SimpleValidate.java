package validate;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleValidate {

    @Test
    public void testName() throws Exception {
        Artifact artifact = new Artifact();
        assertThat("Artifact contains a license file", artifact.contains("LICENSE"));
    }
}