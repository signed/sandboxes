package serialization;

import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

public class Person_SerializationTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void serializeToDisk() throws IOException {
        File file = folder.newFile();
        try {
            Person ted = new Person("Ted", "Neward", 39);
            Person charl = new Person("Charlotte",
                    "Neward", 38);

            ted.setSpouse(charl);
            charl.setSpouse(ted);

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ted);
            oos.close();
        } catch (Exception ex) {
            fail("Exception thrown during test: " + ex.toString());
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Person ted = (Person) ois.readObject();
            ois.close();

            MatcherAssert.assertThat(ted.getFirstName(), is("Ted"));
            MatcherAssert.assertThat(ted.getSpouse().getFirstName(), is("Charlotte"));

        } catch (Exception ex) {
            fail("Exception thrown during test: " + ex.toString());
        }
    }
}
