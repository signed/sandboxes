package dozer;

import org.dozer.CustomFieldMapper;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DoNotMapContentOfCollection implements CustomFieldMapper, MapperAware {

    private Mapper mapper;

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
        Class<?> sourceFieldType = fieldMapping.getSrcFieldType(source.getClass());
        if (!Collection.class.isAssignableFrom(sourceFieldType)) {
            return false;
        }

        Class<?> destinationFieldType = fieldMapping.getDestFieldType(destination.getClass());

        if (List.class.isAssignableFrom(destinationFieldType)) {
            writeInto(destination, new ArrayList(), fieldMapping);
            return true;
        }

        if (Set.class.isAssignableFrom(destinationFieldType)) {
            writeInto(destination, new HashSet(), fieldMapping);
            return true;
        }

        if(Map.class.isAssignableFrom(destinationFieldType)) {
            writeInto(destination, new HashMap(), fieldMapping);
            return true;
        }

        throw new RuntimeException("unknown collection");
    }

    private void writeInto(Object destination, Object object, FieldMap fieldMapping) {
        fieldMapping.writeDestValue(destination, object);
    }
}
