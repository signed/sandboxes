package serialization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ImmutableObject_SerializationTest {

    @TempDir
    private Path folder;

    private ToFileSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new ToFileSerializer(folder);
    }

    @Test
    void canConstructObjectEvenIfThereIsNoDefaultConstructor() throws Exception {
        ImmutableObject original = new ImmutableObject("dawn");
        serializer.write(original);
        ImmutableObject deserialized = serializer.readObject();
        assertThat(deserialized.getStuff(), is("dawn"));
    }
}
