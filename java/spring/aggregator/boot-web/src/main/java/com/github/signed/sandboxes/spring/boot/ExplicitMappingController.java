package com.github.signed.sandboxes.spring.boot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
public class ExplicitMappingController {

    @RequestMapping("/mapped")
    public String mapped() {
        return "mapped: " + LocalDateTime.now(ZoneId.of("UTC"));
    }
}
