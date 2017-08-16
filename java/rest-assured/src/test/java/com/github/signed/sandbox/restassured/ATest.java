package com.github.signed.sandbox.restassured;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.jayway.restassured.RestAssured.expect;

public class ATest {

    @Rule
    public final WireMockRule server = new WireMockRule(8080);

    @Test
    public void basicExample() throws Exception {
        stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withStatus(200)));

        expect()
                .statusCode(200)
        .when()
                .get("health");
    }
}
