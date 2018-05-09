package com.github.signed.sandboxes.spring.boot.caller.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CallerBootApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .showBanner(false)
                .sources(CallerBootApplication.class)
                .run(args);
    }
}
