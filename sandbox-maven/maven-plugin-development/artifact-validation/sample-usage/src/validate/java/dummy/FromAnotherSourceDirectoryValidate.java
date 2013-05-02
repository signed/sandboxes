package dummy;

import org.junit.Test;
import validate.Artifact;

import static org.hamcrest.MatcherAssert.assertThat;

public class FromAnotherSourceDirectoryValidate {

    @Test
    public void testName() throws Exception {
        Artifact artifact = new Artifact();
        assertThat("Artifact contains a license file", artifact.contains("LICENSE"));
    }
}
