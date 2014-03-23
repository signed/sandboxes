package modelmapper;

import converter.Context;
import converter.Property;
import converter.ReplaceWithEmptyCollectionInDestination;
import org.modelmapper.internal.converter.ConverterStore;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

class EmptyCollectionConverter implements ConditionalConverter<Object, Object> {

    private final ConverterStore converterStore = new ConverterStore();
    private final ReplaceWithEmptyCollectionInDestination converter = new ReplaceWithEmptyCollectionInDestination();

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return firstMatch(sourceType, destinationType).match(sourceType, destinationType);
    }

    @Override
    public Object convert(MappingContext<Object, Object> context) {
        Property source = new Property(context.getSource(), context.getSourceType());
        Property destination = new Property(context.getDestination(), context.getDestinationType());
        Context rawContext = new Context(source, destination);

        if (!converter.canConvert(rawContext)) {
            ConditionalConverter<Object, Object> converter = firstMatch(context.getSourceType(), context.getDestinationType());
            return converter.convert(context);
        }

        return converter.convert(rawContext);
    }

    private ConditionalConverter<Object, Object> firstMatch(Class<?> sourceType, Class<?> destinationType) {
        return converterStore.getFirstSupported(sourceType, destinationType);
    }
}
