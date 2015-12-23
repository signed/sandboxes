package example.zuul;

import feign.Feign;
import feign.RequestLine;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ZuulBootApplicationTest {

    public interface ZuulExample{
        @RequestLine("GET /apples")
        String apples();

        @RequestLine("GET /bananas")
        String bananas();
    }

    @Test
    public void retrieve_bananas_via_zuul() throws Exception {
        ZuulExample client = Feign.builder().target(ZuulExample.class, "http://localhost:8035");

        assertThat(client.apples(), is("{ \"fruit\": \"Apple\"}"));
        assertThat(client.bananas(), is("{ \"fruit\": \"Banana\"}"));
    }
}