package com.github.signed.sandbox.restassured;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.MatcherAssert.assertThat;

public class ATest {

    @Rule
    public final WireMockRule server = new WireMockRule(8080);

    @Test
    public void basicExample() throws Exception {
        server.stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withStatus(200)));

        Response response = expect()
                .statusCode(200)
                .when()
                .get("health");


        assertThat(response.getStatusCode(), CoreMatchers.equalTo(200));
    }
}
