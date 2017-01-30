package sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import sample.BootApplication.ResponseText;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BootApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class BootApplicationTest {

    @Value("${local.server.port}")
    int port;

    @Test
    public void callEndpoint() throws Exception {
        ResponseEntity<ResponseText> responseTextResponseEntity = new RestTemplate().exchange(url(), POST, new HttpEntity<>(requestTextIs("meat")), ResponseText.class);
        assertThat(responseTextResponseEntity.getBody().responseText, equalTo("processed(meat)"));
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