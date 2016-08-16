package samples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class CreatePageTO_Test {
    @Test
    public void ensureTransferObjectCanBeSerialized() throws Exception {
        CreatePageTO to = new CreatePageTO.Builder()
                .space("DEMO")
                .title("Automation for the win")
                .content("<p>I like the storage format.</p>").build();
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(to);
        assertThat(json, CoreMatchers.notNullValue());
    }
}