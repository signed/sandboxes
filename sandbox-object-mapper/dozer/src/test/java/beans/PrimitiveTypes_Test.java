package beans;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
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
    public void basicMapping() throws Exception {
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

    @Test
    public void twoPossibleMappings() throws Exception {

        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder one = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(PrimitiveTypes.class, PrimitiveTypes.class);
            }
        };

        BeanMappingBuilder two = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(PrimitiveTypes.class, PrimitiveTypes.class, TypeMappingOptions.mapId("BananaSplit"));
            }
        };
        mapper.addMapping(one);
        mapper.addMapping(two);

        assertThatDozerMappedCorrectlyTo(mapper.map(primitiveTypes, PrimitiveTypes.class, "BananaSplit"));
    }


    private void assertThatDozerMappedCorrectlyTo(PrimitiveTypes mapped) {
        assertThat(mapped.getString(), is("Hello Dozer"));
        assertThat(mapped.get_double(), is(3.3));
        assertThat(mapped.get_int(), is(42));
        assertThat(mapped.get_long(), is(23l));
        assertThat(mapped, not(sameInstance(primitiveTypes)));
    }
}
