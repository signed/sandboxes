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

    public static class CustomDeserializer extends StdDeserializer<Migrated> {

        public CustomDeserializer() {
            this(null);
        }

        public CustomDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Migrated deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            final var token = jsonParser.currentToken();
            if (token.isBoolean()) {
                return JsonToken.VALUE_TRUE.equals(token) ? Migrated.True : Migrated.False;
            }
            return deserializationContext.readValue(jsonParser, Migrated.class);
        }
    }

    public static class Data {
        @JsonDeserialize(using = CustomDeserializer.class)
        public Migrated value;
    }

    @Test
    void oldRepresentationTrueToEnum() throws JsonProcessingException {
        var oldRepresentation = """
                {
                  "value": true
                }
                """;
        assertThat(readDataFrom(oldRepresentation).value, is(Migrated.True));
    }

    @Test
    void oldRepresentationFalseToEnum() throws JsonProcessingException {
        var oldRepresentation = """
                {
                  "value": false
                }
                """;
        assertThat(readDataFrom(oldRepresentation).value, is(Migrated.False));
    }

    @Test
    void newEnumValues() throws JsonProcessingException {
        assertThat(roundTrip(Migrated.True).value, is(Migrated.True));
        assertThat(roundTrip(Migrated.NewValue).value, is(Migrated.NewValue));
        assertThat(roundTrip(Migrated.False).value, is(Migrated.False));
    }

    private Data roundTrip(Migrated value) throws JsonProcessingException {
        final var data = new Data();
        data.value = value;

        final var json = new ObjectMapper().writeValueAsString(data);
        return readDataFrom(json);
    }

    private Data readDataFrom(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Data.class);
    }
}
