package serialization;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ImmutableObject_SerializationTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private final ToFileSerializer serializer = new ToFileSerializer(folder);

    @Test
    public void canConstructObjectEvenIfThereIsNoDefaultConstructor() throws Exception {
        ImmutableObject original = new ImmutableObject("dawn");
        serializer.write(original);
        ImmutableObject deserialized = serializer.readObject();
        assertThat(deserialized.getStuff(), is("dawn"));
    }
}
