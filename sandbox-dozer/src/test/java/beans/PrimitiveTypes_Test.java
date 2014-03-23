package beans;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrimitiveTypes_Test {
    private final PrimitiveTypes primitiveTypes = new PrimitiveTypes();

    @Before
    public void initializeSourceObject() {
        primitiveTypes.set_double(3.3);
        primitiveTypes.set_int(42);
        primitiveTypes.set_long(23);
        primitiveTypes.setString("Hello Dozer");
    }

    @Test
    public void testName() throws Exception {
        BeanMappingBuilder builder = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(PrimitiveTypes.class, PrimitiveTypes.class);
            }
        };

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(builder);

        assertThatDozerMappedCorrectlyTo(mapper.map(primitiveTypes, PrimitiveTypes.class));
    }

    private void assertThatDozerMappedCorrectlyTo(PrimitiveTypes mapped) {
        assertThat(mapped.getString(), is("Hello Dozer"));
        assertThat(mapped.get_double(), is(3.3));
        assertThat(mapped.get_int(), is(42));
        assertThat(mapped.get_long(), is(23l));
        assertThat(mapped, not(sameInstance(primitiveTypes)));
    }
}
