package com.github.signed.sandboxes.spring.boot;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BuildNumberController {

    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<?> home() {
        return ResponseEntity.ok(LocalDateTime.now() + "Hello World!");
    }


}
