package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
public class SampleController {
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }
}
