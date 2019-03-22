package modelmapper;

import beans.PrimitiveTypes;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import testsuite.PrimitiveTypes_Test;

public class PrimitiveTypesModelMapper_Test extends PrimitiveTypes_Test {

    @Override
    public PrimitiveTypes mapIntoAnotherInstance(PrimitiveTypes source) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(PrimitiveTypes.class, PrimitiveTypes.class);
        return modelMapper.map(source, PrimitiveTypes.class);
    }

    @Test
    public void testName() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<PrimitiveTypes, PrimitiveTypes> typeMap = modelMapper.createTypeMap(PrimitiveTypes.class, PrimitiveTypes.class);
        typeMap.validate();
        modelMapper.validate();

    }
}
