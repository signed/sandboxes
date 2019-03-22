package dummy;

import org.junit.Test;
import some.Artifact;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;

public class FromAnotherSourceDirectoryValidate {

    @Test
    public void testName() throws Exception {
        Artifact artifact = new Artifact();
        assertThat("Artifact contains a license file", artifact.contains("LICENSE"));
    }

    @Test
    public void dumpSystemProperties() throws Exception {
        Properties properties = System.getProperties();
        System.out.println("dumping artifacts from system properties");
        for (Object o : properties.keySet()) {
            if(o.toString().startsWith("maven.artifact")){
                System.out.println(o.toString());
            }
        }
    }
}