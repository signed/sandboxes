package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WrapStringInDto_Test {

    public static class StringWrapper {
        public final String value;

        public StringWrapper(String value) {
            this.value = value;
        }
    }

    public static class ToSerialize {
        private StringWrapper member = new StringWrapper("47");

        public StringWrapper getMember(){
            return member;
        }
    }


    public static class MyTypeAdapter implements JsonSerializer<StringWrapper>, JsonDeserializer<StringWrapper> {

        @Override
        public JsonElement serialize(StringWrapper src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.value);
        }

        @Override
        public StringWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new StringWrapper(json.getAsString());
        }
    }

    private GsonBuilder builder = new GsonBuilder();
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        builder.registerTypeAdapter(StringWrapper.class, new MyTypeAdapter());
        gson = builder.create();

    }

    @Test
    public void deSerializeWrappedStringAsSimpleValue() throws Exception {
        String gsonString = gson.toJson(new ToSerialize());
        assertThat(gsonString, is("{\"member\":\"47\"}"));
    }

    @Test
    public void wrapSimpleStringWithStringWrapper() throws Exception {
        String json = "{member:49}";
        ToSerialize deSerialized = gson.fromJson(json, ToSerialize.class);

        assertThat(deSerialized.getMember().value, is("49"));
    }

}
