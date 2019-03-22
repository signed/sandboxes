package gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MapFieldNameToADifferentJsonName_Test {

    private Gson gson;

    private static class ToSerialize {
        final String dtoName;
        final Integer another;

        public ToSerialize() {
            another = 7;
            dtoName = "value";
        }
    }

    private static class AnotherClassWithAFieldNamedDtoName{
        final Long dtoName = 123l;
    }

    private final GsonBuilder builder = new GsonBuilder();

    @Before
    public void configureGsonToMapTheFiled_dtoName_to_jsonName_InClass_ToSerialize() throws Exception {
        FieldNamingStrategy strategy = new ConfigurableFieldNamingStrategy();
        builder.setFieldNamingStrategy(strategy);
        gson = builder.create();
    }

    @Test
    public void renameTheConfiguredFieldInTheJsonRepresentation() throws Exception {
        assertThat(gson.toJson(new ToSerialize()), containsString("\"jsonName\":\"value\""));
    }

    @Test
    public void doNotChangeTheFieldNameForOtherMembers() throws Exception {
        assertThat(gson.toJson(new ToSerialize()), containsString("\"another\":7"));
    }

    @Test
    public void deSerializeTheRenamedFieldCorrectly() throws Exception {
        assertThat(gson.fromJson("{jsonName:\"juhu\"}", ToSerialize.class).dtoName, is("juhu"));
    }

    @Test
    public void onlyRenameTheFieldInTheConfiguredClass() throws Exception {
        assertThat(gson.toJson(new AnotherClassWithAFieldNamedDtoName()), containsString("\"dtoName\":123"));
    }

    private static class ConfigurableFieldNamingStrategy implements FieldNamingStrategy {
        private final FieldNamingStrategy fallback = FieldNamingPolicy.IDENTITY;

        @Override
        public String translateName(Field f) {
            if (ToSerialize.class.equals(f.getDeclaringClass()) && "dtoName".equals(f.getName())) {
                return "jsonName";
            }
            return fallback.translateName(f);
        }
    }
}
