package serialization;

import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.hamcrest.Matchers.is;

public class Person_SerializationTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private ToFileSerializer serializer = new ToFileSerializer(folder);


    @Test
    public void serializeToDisk() throws IOException, ClassNotFoundException {
        Person ted = new Person("Ted", "Neward", 39);
        Person charl = new Person("Charlotte", "Neward", 38);

        ted.setSpouse(charl);
        charl.setSpouse(ted);

        serializer.write(ted);

        Person deserializedTed = serializer.readObject();

        MatcherAssert.assertThat(deserializedTed.getFirstName(), is("Ted"));
        MatcherAssert.assertThat(deserializedTed.getSpouse().getFirstName(), is("Charlotte"));
    }
}