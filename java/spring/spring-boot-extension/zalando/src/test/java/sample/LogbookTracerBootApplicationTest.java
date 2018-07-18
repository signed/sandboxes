package sample;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class LogbookTracerBootApplicationTest {

    @Value("${local.server.port}")
    int port;

    @Test
    void call() {
        assertThat(process("meat").getBody().responseText, equalTo("processed(meat)"));
        assertThat(process("water").getBody().responseText, equalTo("processed(water)"));
    }

    private ResponseEntity<LogbookTracerBootApplication.ResponseText> process(String toProcess) {
        return new RestTemplate().exchange(url(), POST, new HttpEntity<>(requestTextIs(toProcess)), LogbookTracerBootApplication.ResponseText.class);
    }

    private LogbookTracerBootApplication.RequestText requestTextIs(String text) {
        LogbookTracerBootApplication.RequestText requestText = new LogbookTracerBootApplication.RequestText();
        requestText.requestText = text;
        return requestText;
    }

    private String url() {
        return "http://localhost:" + port;
    }
}