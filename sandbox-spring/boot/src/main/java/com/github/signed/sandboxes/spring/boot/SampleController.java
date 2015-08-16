package com.github.signed.sandboxes.spring.boot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
public class SampleController {
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return LocalDateTime.now() +" Hello World!";
    }
}
