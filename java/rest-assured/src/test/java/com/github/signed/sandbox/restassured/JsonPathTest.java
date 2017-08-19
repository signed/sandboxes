package com.github.signed.sandbox.restassured;

import com.jayway.restassured.path.json.JsonPath;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonPathTest {

    private static class To {
        public String hello;
    }

    @Test
    public void extractObjects() throws Exception {
        To object = new JsonPath("{\n" +
                "  \"foo\": {\n" +
                "    \"hello\": \"world\"\n" +
                "  }\n" +
                "}").getObject("foo", To.class);
        assertThat(object.hello, equalTo("world"));
    }


}
