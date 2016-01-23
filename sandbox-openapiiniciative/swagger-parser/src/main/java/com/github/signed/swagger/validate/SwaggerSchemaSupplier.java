package com.github.signed.swagger.validate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SwaggerSchemaSupplier implements Supplier<String> {
    private final String resource = "schema.json";

    @Override
    public String get() {
        Stream<String> stream = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(resource))).lines();
        return stream.reduce("", (s, s2) -> s + "\n" + s2);
    }
}
