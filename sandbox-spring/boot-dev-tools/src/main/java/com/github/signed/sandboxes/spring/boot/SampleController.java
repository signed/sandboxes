package com.github.signed.sandboxes.spring.boot;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SampleController {

    private final Collaborator collaborator;

    @Autowired
    public SampleController(Collaborator collaborator) {
        this.collaborator = collaborator;
    }


    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<?> home() {
        return ResponseEntity.ok(LocalDateTime.now() + "Hello World!");
    }

    @RequestMapping("/injected")
    @ResponseBody
    public ResponseEntity<?> injected() {
        return ResponseEntity.ok(collaborator.message());
    }

}
