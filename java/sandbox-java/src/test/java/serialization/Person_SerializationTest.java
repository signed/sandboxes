package serialization;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.hamcrest.Matchers.is;

class Person_SerializationTest {

    @TempDir
    private Path folder;

    private ToFileSerializer serializer;


    @BeforeEach
    void setUp() {
        serializer = new ToFileSerializer(folder);
    }

    @Test
    void serializeToDisk() throws IOException, ClassNotFoundException {
        Person ted = new Person("Ted", "Neward", 39);
        Person charlotte = new Person("Charlotte", "Neward", 38);

        ted.setSpouse(charlotte);
        charlotte.setSpouse(ted);

        serializer.write(ted);

        Person deserializedTed = serializer.readObject();

        MatcherAssert.assertThat(deserializedTed.getFirstName(), is("Ted"));
        MatcherAssert.assertThat(deserializedTed.getSpouse().getFirstName(), is("Charlotte"));
    }
}