package com.github.signed.spring.cors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

    //https://spring.io/guides/gs/rest-service-cors/
    //https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-cors
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/greet")
    @CrossOrigin()
    public String get() {
        return "{}";
    }

    @PostMapping("/greet")
    @CrossOrigin
    public String post() {
        return "{}";
    }
}
