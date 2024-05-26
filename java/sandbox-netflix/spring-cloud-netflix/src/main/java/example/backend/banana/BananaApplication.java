package example.backend.banana;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class BananaApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
            .bannerMode(Banner.Mode.OFF)
            .sources(BananaApplication.class)
            .profiles("banana")
            .run(args);
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String echoTransferObject() {
        return "{ \"fruit\": \"Banana\"}";
    }
}
