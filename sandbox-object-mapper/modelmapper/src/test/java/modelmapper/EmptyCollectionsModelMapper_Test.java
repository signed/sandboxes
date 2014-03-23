package modelmapper;

import beans.ClassWithCollections;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import testsuite.EmptyCollections_Test;

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

        return mapper.map(classWithCollections, ClassWithCollections.class);
    }
}
