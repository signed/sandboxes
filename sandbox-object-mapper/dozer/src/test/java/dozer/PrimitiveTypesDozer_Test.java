package dozer;

import beans.PrimitiveTypes;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.junit.Test;
import testsuite.PrimitiveTypes_Test;

public class PrimitiveTypesDozer_Test extends PrimitiveTypes_Test {

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

        assertThatMappedCorrectlyTo(mapper.map(primitiveTypes, PrimitiveTypes.class, "BananaSplit"));
    }

    public PrimitiveTypes mapIntoAnotherInstance(PrimitiveTypes primitiveTypes) {
        BeanMappingBuilder builder = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(PrimitiveTypes.class, PrimitiveTypes.class);
            }
        };
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(builder);
        return mapper.map(primitiveTypes, PrimitiveTypes.class);
    }
}
