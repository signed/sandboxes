package com.github.signed.sandbox.restassured;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

public class ATest {

    @Rule
    public final WireMockRule server = new WireMockRule(8080);

    @Test
    public void basicExample() throws Exception {
        stubHealthJsonToReturn("{}");

        Response response = expect()
                .statusCode(200)
                .when()
                .get("health");

        assertThat(response.getStatusCode(), equalTo(200));
    }

    @Test
    public void assertJsonBody() throws Exception {
        stubHealthJsonToReturn("{\n" +
                "  \"one\": 1,\n" +
                "  \"array\": [\n" +
                "    \"2\",\n" +
                "    \"zwei\"\n" +
                "  ],\n" +
                "  \"object\": {\n" +
                "    \"foo\": null,\n" +
                "    \"bar\": 7,\n" +
                "    \"baz\": \"hello world\"\n" +
                "  }\n" +
                "}");
        expect()
            .body("one", equalTo(1))
            .root("object")
                .body("foo", nullValue())
                .body("bar", equalTo(7))
                .body("baz", equalTo("hello world"))
            .root("")
            .body("array", hasItems("zwei", "2"))
        .when()
            .get("health");
    }

    private void stubHealthJsonToReturn(String body) {
        server.stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse().withHeader("content-type", "application/json")
                        .withStatus(200)
                        .withBody(body)
                )
        );
    }
}
