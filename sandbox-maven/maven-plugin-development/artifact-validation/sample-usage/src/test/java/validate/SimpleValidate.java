package validate;

import org.junit.Test;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleValidate {
    @Test
    public void testName() throws Exception {
        String artifact = System.getProperties().getProperty("artifact", "not present");
        assertThat("Artifact contains a license file", ZipUtil.containsEntry(new File(artifact), "LICENSE"));
    }
}
