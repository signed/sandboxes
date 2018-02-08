package sample;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class Runner {

    static class Bean1599 {
        public int id;
        public Object obj;
    }

    @Test
    void name() {
        final String JSON = "{\"id\": 124,\n" +
                " \"obj\":[ \"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\n" +
                "  {\n" +
                "    \"transletBytecodes\" : [ \"AAIAZQ==\" ],\n" +
                "    \"transletName\" : \"a.b\",\n" +
                "    \"outputProperties\" : { }\n" +
                "  }\n" +
                " ]\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();

        Exception invalidDefinitionException = assertThrows(Exception.class, () -> mapper.readValue(JSON, Bean1599.class));
        Assertions.assertThat(invalidDefinitionException)
                .hasMessageContaining("Invalid type")
                .hasMessageContaining("to deserialize")
                .hasMessageContaining("prevented for security reasons");
    }
}
