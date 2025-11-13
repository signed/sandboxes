package jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.impl.FailingDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BannedFromJacksonTest {

    @Target({ElementType.FIELD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Banned {
    }

    static class BreakBeanDeserializationIfBanned extends BeanDeserializerModifier {

        @Override
        public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
            var cls = Banned.class;
            if (beanDesc.getClassAnnotations().has(cls)) {
                return new FailingDeserializer("Classes annotated with @%s are not allowed to be deserialized!".formatted(cls.getSimpleName()));
            }
            return super.modifyDeserializer(config, beanDesc, deserializer);
        }
    }

    static class BreakBeanSerializationIfBanned extends BeanSerializerModifier {

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            if (beanProperties.stream().anyMatch(bp -> bp.getMember().hasAnnotation(Banned.class))) {
                return List.of(new BeanPropertyWriter() {
                    @Override
                    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov)
                            throws IOException {
                        throw new JsonMappingException(gen, "Serialization blocked for field");
                    }
                });
            }
            return super.changeProperties(config, beanDesc, beanProperties);
        }

        @Override
        public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
            var cls = Banned.class;
            if (beanDesc.getClassAnnotations().has(cls)) {
                return new FailingSerializer("Classes annotated with @%s are not allowed to be serialized!".formatted(cls.getSimpleName()));
            }
            return super.modifySerializer(config, beanDesc, serializer);
        }
    }

    static class JacksonBouncerModule extends SimpleModule {
        public JacksonBouncerModule() {
            super("JacksonBouncerModule");
            setSerializerModifier(new BreakBeanSerializationIfBanned());
            setDeserializerModifier(new BreakBeanDeserializationIfBanned());
        }
    }

    @Banned
    public static class NotAllowedToBeSerialized {

    }

    @Banned
    public static class NotAllowedToBeDeserialized {
    }

    public static class TopLevel {
        @Banned
        public static class NestedBanned {

        }

        public NestedBanned nestedBanned;
    }

    @Test
    void preventSerialization() {
        assertThatThrownBy(() -> getObjectMapper().writeValueAsString(new NotAllowedToBeSerialized()))
                .isInstanceOf(JsonMappingException.class).hasMessage("Classes annotated with @Banned are not allowed to be serialized!");
    }

    @Test
    void preventNestedSerialization() {
        var value = new TopLevel();
        value.nestedBanned = new TopLevel.NestedBanned();
        assertThatThrownBy(() -> getObjectMapper().writeValueAsString(value))
                .isInstanceOf(JsonMappingException.class).hasMessageStartingWith("Classes annotated with @Banned are not allowed to be serialized!");
    }

    @Test
    void preventDeserialization() {
        assertThatThrownBy(() -> getObjectMapper().readValue("{}", NotAllowedToBeDeserialized.class))
                .isInstanceOf(JsonMappingException.class).hasMessageStartingWith("Classes annotated with @Banned are not allowed to be deserialized!");
    }

    @Test
    void preventNestedDeserialization() {
        assertThatThrownBy(() -> getObjectMapper().readValue("{\"nestedBanned\": {}}", NotAllowedToBeDeserialized.class))
                .isInstanceOf(JsonMappingException.class).hasMessageStartingWith("Classes annotated with @Banned are not allowed to be deserialized!");
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper().registerModule(new JacksonBouncerModule());
    }
}
