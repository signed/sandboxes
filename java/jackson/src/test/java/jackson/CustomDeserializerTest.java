package jackson;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CustomDeserializerTest {

    public enum Migrated {
        True,
        NewValue,
        False
    }

    public static class CustomDeserializer extends StdDeserializer<Data>{

        public CustomDeserializer() {
            this(null);
        }

        public CustomDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Data deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            final var data = new Data();
            final var tree = jsonParser.getCodec().readTree(jsonParser);
            final var node = tree.get("value");
            final var token = node.asToken();
            if(token.isBoolean()){
                data.value = JsonToken.VALUE_TRUE.equals(token)?Migrated.True : Migrated.False;
            }
            return data;
        }
    }

    @JsonDeserialize(using = CustomDeserializer.class)
    public static class Data {
        public Migrated value;
    }

    @Test
    void oldRepresentationTrueToEnum() throws JsonProcessingException {
        var oldRepresentation = """
                {
                  "value": true
                }
                """;
        final var data = new ObjectMapper().readValue(oldRepresentation, Data.class);
        assertThat(data.value, is(Migrated.True));
    }

    @Test
    void oldRepresentationFalseToEnum() throws JsonProcessingException {
        var oldRepresentation = """
                {
                  "value": false
                }
                """;
        final var data = new ObjectMapper().readValue(oldRepresentation, Data.class);
        assertThat(data.value, is(Migrated.False));
    }
}
