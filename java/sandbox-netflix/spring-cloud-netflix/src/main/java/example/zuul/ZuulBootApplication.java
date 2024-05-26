package example.zuul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulBootApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZuulBootApplication.class).profiles("zuul").web(true).run(args);
    }
}
