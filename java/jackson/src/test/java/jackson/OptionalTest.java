package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class OptionalTest {

    public static class Dto {
        public Optional<String> name;
    }

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());
    private String json;

    @Test
    void property_not_contained_in_json() throws Exception {
        json = "{}";

        assertThat(dto().name, nullValue());
    }

    @Test
     void property_is_null_in_json() throws Exception {
        json = "{\n" +
                "  \"name\": null\n" +
                "}";

        assertThat("should not be present", !dto().name.isPresent());
    }

    @Test
     void property_is_set_in_json() throws Exception {
        json = "{\n" +
                "  \"name\": \"Tom\"\n" +
                "}";

        assertThat(dto().name.get(), equalTo("Tom"));
    }

    private Dto dto() throws java.io.IOException {
        return mapper.readValue(json, Dto.class);
    }
}
