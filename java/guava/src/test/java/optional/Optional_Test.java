package optional;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Optional_Test {

    @Test
    public void anAbsent() throws Exception {
        Optional<String> absent = Optional.absent();

        assertThat(absent.or("default"), is("default"));
    }

    @Test
    public void fromNullable() throws Exception {
        Optional<String> fromNullable = Optional.fromNullable(null);

        assertThat(fromNullable.or("default"), is("default"));
    }

    @Test
    public void returnTheActualValueIfItIsNotNull() throws Exception {
        Optional<String> original = Optional.of("original");
        assertThat(original.or("default"), is("original"));
    }

    @Test(expected = NullPointerException.class)
    public void ofWithNullArgumentDoesNotWork() throws Exception {
        Optional.<String>of(null);
    }
}
