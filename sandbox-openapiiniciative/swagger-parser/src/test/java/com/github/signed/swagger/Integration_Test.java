package com.github.signed.swagger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;
import io.swagger.util.Yaml;

public class Integration_Test {

    private final String first = TestFiles.Json.petstoreExample();
    private final String second = TestFiles.Json.petstoreExample();
    private final SwaggerParser parser = new SwaggerParser();
    private final SwaggerReduce reduce = SwaggerReduce.publicAsMarkerTag();
    private final SwaggerTrim trim = new SwaggerTrim();
    private final SwaggerMerger merge = new SwaggerMerger();

    @Test
    public void just_reduce() throws Exception {
        Swagger petShop = parser.read(first);
        petShop.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(a -> a.tag("public"));
        reduce.reduce(petShop);

        Yaml.prettyPrint(petShop);
    }

    @Test
    public void reduce_trim() throws Exception {
        Swagger petShop = parser.read(first);
        petShop.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(a -> a.tag("public"));
        reduce.reduce(petShop);

        Yaml.prettyPrint(petShop);

        trim.trim(petShop);

        Yaml.prettyPrint(petShop);
    }

    @Test
    public void reduce_trim_merge() throws Exception {
        Swagger _1st = parser.read(first);
        _1st.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(a -> a.tag("public"));
        Swagger _2nd = parser.read(second);
        List<Swagger> collect = Stream.of(_1st, _2nd).map(reduce::reduce).map(trim::trim).collect(Collectors.toList());
        Swagger result = this.merge.merge(collect.get(0), collect.get(1));
        Yaml.prettyPrint(_1st);
        Json.prettyPrint(result);
    }

    @Test
    public void load_map_definition_found_on_the_web() throws Exception {
        Swagger mapDefinition = parser.read(TestFiles.Yaml.mapDefinition());
        System.out.println(mapDefinition);
    }

    @Test
    public void model_with_composition() throws Exception {
        Swagger swagger = parser.read(TestFiles.Yaml.modelWithComposition());
        Swagger trim = this.trim.trim(swagger);
        Yaml.prettyPrint(trim);
        assertThat(trim.getDefinitions().size(), is(2));
    }
}
