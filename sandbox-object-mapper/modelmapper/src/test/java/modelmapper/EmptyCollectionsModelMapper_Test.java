package modelmapper;

import beans.ClassWithCollections;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.spi.ConditionalConverter;
import testsuite.EmptyCollections_Test;

import java.util.List;

public class EmptyCollectionsModelMapper_Test extends EmptyCollections_Test {

    @Override
    protected ClassWithCollections emptyCollectionsWhileMapping(ClassWithCollections classWithCollections) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<ClassWithCollections, ClassWithCollections> typeMap = mapper.createTypeMap(ClassWithCollections.class, ClassWithCollections.class);
        typeMap.validate();

//        typeMap.setPreConverter(new Converter<ClassWithCollections, ClassWithCollections>() {
//            @Override
//            public ClassWithCollections convert(MappingContext<ClassWithCollections, ClassWithCollections> context) {
//                System.out.println("juhu");
//                return null;
//            }
//        });
        Configuration configuration = mapper.getConfiguration();
        List<ConditionalConverter<?, ?>> converters = configuration.getConverters();
        converters.clear();
        converters.add(new EmptyCollectionConverter());

        return mapper.map(classWithCollections, ClassWithCollections.class);
    }

}
