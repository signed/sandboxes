package com.github.signed.demo.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// https://spring.io/guides/gs/testing-web
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringSecurityControllerTest {
    //todo setup users in the test
    //todo figure out why @WithMockUser does not work https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/authentication.html

    @Test
    void accessTheServerPort(@LocalServerPort int port) {
        assertThat(port).isGreaterThan(1023);
    }

    @Test
    void allowAdminsToAccessTheResource(@LocalServerPort int port, @Autowired TestRestTemplate restTemplate) {
        final String url = "http://localhost:" + port + "/security/admin";

        final HttpEntity<Void> entity = new HttpEntity<>(createHeaders("annabelle", "annabelle"));
        final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        assertThat(exchange.getBody()).contains("Congratulations! You are an Admin");
    }

    @Test
    void keepUsersOut(@LocalServerPort int port, @Autowired TestRestTemplate restTemplate) {
        final String url = "http://localhost:" + port + "/security/admin";

        final HttpEntity<Void> entity = new HttpEntity<>(createHeaders("ulf", "ulf"));
        final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            final byte[] bytes = auth.getBytes(StandardCharsets.US_ASCII);
            byte[] encodedAuth = Base64.getEncoder().encode(bytes);
            String authHeader = "Basic " + new String(encodedAuth);
            System.out.println(authHeader);
            set("Authorization", authHeader);
        }};
    }

}
