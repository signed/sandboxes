package com.github.signed.swagger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;

public class Integration_Test {

    private final String first = TestFiles.petstoreExample();
    private final String second = TestFiles.petstoreExample();
    private final SwaggerParser parser = new SwaggerParser();
    private final SwaggerCleanUp cleanUp = new SwaggerCleanUp();
    private final SwaggerTrim trim = new SwaggerTrim();
    private final SwaggerMerger merge = new SwaggerMerger();

    @Test
    public void name() throws Exception {
        Swagger _1st = parser.read(first);
        Swagger _2nd = parser.read(second);
        List<Swagger> collect = Stream.of(_1st, _2nd).map(cleanUp::cleanup).map(trim::trim).collect(Collectors.toList());
        Swagger result = this.merge.merge(collect.get(0), collect.get(1));
        System.out.println(Json.pretty(result));
    }
}
