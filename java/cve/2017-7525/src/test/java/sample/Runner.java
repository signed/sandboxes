package sample;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Spark;

class Runner {

    /**
     * https://adamcaudill.com/2017/10/04/exploiting-jackson-rce-cve-2017-7525/
     * https://github.com/mbechler/marshalsec
     */

    static class Bean1599 {
        public int id;
        public Object obj;
    }


    @BeforeEach
    void setUp() {
        Spark.port(8080);
        Spark.get("/", (request,  response) -> {
            System.out.println("gotcha");
            return "hello";
        });
        Spark.awaitInitialization();
    }

    @AfterEach
    void tearDown() {
        Spark.stop();
    }

    @Test
    void testSpark() throws IOException {
        URL url = new URL("http://localhost:8080/");
        System.out.println(url.getPort());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        StringBuilder result = new StringBuilder();
        while((line = rd.readLine()) != null) {
            result.append(line);
        }

        rd.close();

        System.out.println(result);
    }

    @Test
    void name() throws IOException {
        Path absolute = Paths.get("out/test/classes/sample/Payload.class");
        byte[] bytes = Files.readAllBytes(absolute);
        String base64Encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println(base64Encoded);


        String payload = "AAIAZQ==";
        payload = base64Encoded;
        final String JSON = "{\"id\": 124,\n" +
                " \"obj\":[ \"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\n" +
                "  {\n" +
                "    \"transletBytecodes\" : [ \"" + payload + "\" ],\n" +
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
