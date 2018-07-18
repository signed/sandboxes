package sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import sample.BootApplication.ResponseText;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BootApplicationTest {

    @Value("${local.server.port}")
    int port;

    @Test
    public void callEndpoint() {
        assertThat(process("meat").getBody().responseText, equalTo("processed(meat)"));
        assertThat(process("water").getBody().responseText, equalTo("processed(water)"));
    }

    private ResponseEntity<ResponseText> process(String toProcess) {
        return new RestTemplate().exchange(url(), POST, new HttpEntity<>(requestTextIs(toProcess)), ResponseText.class);
    }

    private BootApplication.RequestText requestTextIs(String text) {
        BootApplication.RequestText requestText = new BootApplication.RequestText();
        requestText.requestText = text;
        return requestText;
    }

    private String url() {
        return "http://localhost:" + port;
    }
}