package com.github.signed.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.signed.demo.http.GetDemosDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.google.common.truth.Truth.assertThat;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    private ServletWebServerApplicationContext webServer;

    @Test
    void basicStringResponse() {
        assertThat(fetchString(base().GET())).isEqualTo("Hello");
    }

    @Test
    void status404ForNotExistingEndpoint() {
        final HttpResponse<String> response = fetch(base("/does-not-exist").GET(), ofString());
        assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    void demos() throws IOException {
        final HttpResponse<String> response = fetch(base("/demos").GET(), ofString());
        if (response.statusCode() != HttpStatus.OK.value()) {
            fail("expected HTTP 200");
        }
        final String body = response.body();
        final ObjectMapper mapper = new ObjectMapper();
        final GetDemosDto dto = mapper.reader().readValue(body, GetDemosDto.class);
        assertThat(dto.demos).hasSize(2);
    }

    private HttpRequest.Builder base(String... paths) {
        final int port = this.webServer.getWebServer().getPort();
        final URI uri = URI.create("http://localhost:" + port + String.join("/", paths));
        return HttpRequest.newBuilder(uri);
    }

    private String fetchString(HttpRequest.Builder request) {
        return fetch(request, ofString()).body();
    }

    private <T> HttpResponse<T> fetch(HttpRequest.Builder request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        try {
            try (HttpClient client = HttpClient.newHttpClient()) {
                return client.send(request.build(), responseBodyHandler);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
