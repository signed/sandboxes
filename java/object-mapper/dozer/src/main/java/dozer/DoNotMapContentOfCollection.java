package dozer;

import converter.Context;
import converter.Property;
import converter.ReplaceWithEmptyCollectionInDestination;
import org.dozer.CustomFieldMapper;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;

public class DoNotMapContentOfCollection implements CustomFieldMapper, MapperAware {

    private final ReplaceWithEmptyCollectionInDestination converter = new ReplaceWithEmptyCollectionInDestination();
    private Mapper mapper;

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
        Property sourceProperty = new Property(source, fieldMapping.getSrcFieldType(source.getClass()));
        Property destinationProperty = new Property(destination, fieldMapping.getDestFieldType(destination.getClass()));
        Context context = new Context(sourceProperty, destinationProperty);

        if (!converter.canConvert(context)) {
            return false;
        }
        Object converted = converter.convert(context);
        fieldMapping.writeDestValue(destination, converted);
        return true;
    }

}
