package com.github.signed.sandbox.restassured;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseBuilder;
import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.FilterContext;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import com.jayway.restassured.specification.FilterableResponseSpecification;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
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

    @Test
    public void thereIsJsonPath() throws Exception {
        stubHealthJsonToReturn("{\n" +
                "  \"boolean\": true\n" +
                "}");

        String json = RestAssured.given().filter(new Filter() {
            @Override
            public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
                Response response = ctx.next(requestSpec, responseSpec);

                try {
                    ObjectNode json = response.as(ObjectNode.class);
                    json.put("filter", "value");
                    String manipulated = new ObjectMapper().writeValueAsString(json);
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(manipulated.getBytes(StandardCharsets.UTF_8));
                    return new ResponseBuilder().clone(response).setBody(byteArrayInputStream).build();
                } catch (Exception ex) {
                    return response;
                }
            }
        }).get("health").asString();

        assertThat("should be there", from(json).<Boolean>get("boolean"));
        assertThat("filter for the win", from(json).<String>get("filter"), equalTo("value"));
    }

    private void stubHealthJsonToReturn(String body) {
        server.stubFor(WireMock.get(urlEqualTo("/health"))
                .willReturn(aResponse().withHeader("content-type", "application/json")
                        .withStatus(200)
                        .withBody(body)
                )
        );
    }
}
