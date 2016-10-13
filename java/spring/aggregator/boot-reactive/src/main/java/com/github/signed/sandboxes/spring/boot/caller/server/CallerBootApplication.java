package com.github.signed.sandboxes.spring.boot.caller.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.springframework.boot.Banner.Mode.OFF;

@SpringBootApplication
public class CallerBootApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .bannerMode(OFF)
                .sources(CallerBootApplication.class)
                .run(args);
    }
}
