package testsuite;

import beans.PrimitiveTypes;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class PrimitiveTypes_Test {
    protected final PrimitiveTypes primitiveTypes = new PrimitiveTypes();

    @Before
    public void initializeSourceObject() {
        primitiveTypes.set_double(3.3);
        primitiveTypes.set_int(42);
        primitiveTypes.set_long(23);
        primitiveTypes.setString("Hello Dozer");
    }

    @Test
    public void basicMapping() throws Exception {
        assertThatMappedCorrectlyTo(mapIntoAnotherInstance(primitiveTypes));
    }

    public abstract PrimitiveTypes mapIntoAnotherInstance(PrimitiveTypes source);


    protected void assertThatMappedCorrectlyTo(PrimitiveTypes mapped) {
        assertThat(mapped.getString(), is("Hello Dozer"));
        assertThat(mapped.get_double(), is(3.3));
        assertThat(mapped.get_int(), is(42));
        assertThat(mapped.get_long(), is(23l));
        assertThat(mapped, not(sameInstance(primitiveTypes)));
    }
}
