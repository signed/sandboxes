package converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReplaceWithEmptyCollectionInDestination {

    public boolean canConvert(Context rawContext) {
        return Collection.class.isAssignableFrom(rawContext.source.declaredType);
    }

    public Object convert(Context rawContext) {
        Class<?> destinationFieldType = rawContext.destination.declaredType;

        if (List.class.isAssignableFrom(destinationFieldType)) {
            return new ArrayList();
        }

        if (Set.class.isAssignableFrom(destinationFieldType)) {
            return new HashSet();
        }

        if (Map.class.isAssignableFrom(destinationFieldType)) {
            return new HashSet();
        }

        throw new RuntimeException("uncovered collection type");
    }
}
