package sample;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertThrows;

class Runner {

    /**
     * https://adamcaudill.com/2017/10/04/exploiting-jackson-rce-cve-2017-7525/
     * https://github.com/mbechler/marshalsec
     */

    static class Bean1599 {
        public int id;
        @JsonTypeInfo(use= JsonTypeInfo.Id.CLASS)
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
	void viaObjectMapper() throws IOException {
		String payload = Base64.getEncoder().encodeToString(byteCode());
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
		//mapper.enableDefaultTyping();


		Exception invalidDefinitionException = assertThrows(Exception.class, () -> mapper.readValue(JSON, Bean1599.class));
		Assertions.assertThat(invalidDefinitionException)
				.hasMessageContaining("Invalid type")
				.hasMessageContaining("to deserialize")
				.hasMessageContaining("prevented for security reasons");
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
	void directlyTestExecution() throws Exception {
		TemplatesImpl templates = new TemplatesImpl();

		//Field field = templates.getClass().getDeclaredField("_tfactory");
		//field.setAccessible(true);
		//field.set(templates, new TransformerFactoryImpl());

		Method transletName = templates.getClass().getDeclaredMethod("setTransletName", String.class);
		transletName.setAccessible(true);
		transletName.invoke(templates, "a.b");
		byte[] bytes = byteCode();
		byte[][] baba = {bytes};

		Method method = templates.getClass().getDeclaredMethod("setTransletBytecodes", baba.getClass());

		method.setAccessible(true);
		method.invoke(templates, (Object) baba);

		templates.getOutputProperties();
	}

	private byte[] byteCode() throws IOException {
		return Files.readAllBytes(Paths.get("out/test/classes/sample/Payload.class"));
	}
}
