package com.github.signed.demo.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }

}
