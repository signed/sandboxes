package dozer;

import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmptyCollections extends DozerConverter<Object, Object> implements MapperAware {

    private Mapper mapper;

    public EmptyCollections(Class prototypeA, Class prototypeB) {
        super(Object.class, Object.class);
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object convertTo(Object source, Object destination) {
        if(source.getClass().isAssignableFrom(Collection.class)){
            if(destination.getClass().isAssignableFrom(List.class)){
                return new ArrayList<Object>();
            }
        }

        return mapper.map(source, destination.getClass());
    }

    @Override
    public Object convertFrom(Object source, Object destination) {
        return convertTo(source, destination);
    }
}
