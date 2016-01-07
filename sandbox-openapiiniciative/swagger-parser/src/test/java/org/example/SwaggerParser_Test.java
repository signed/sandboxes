package org.example;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.junit.Test;

public class SwaggerParser_Test {

    @Test
    public void first_attempt() throws Exception {
        SwaggerParser swaggerParser = new SwaggerParser();
        Swagger petstore = swaggerParser.read("src/test/resources/petstore-expanded.json");
        System.out.println(petstore);

    }
}
