package com.github.signed.sandboxes.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
public class SampleController {

    private final Collaborator collaborator;

    @Autowired
    public SampleController(Collaborator collaborator) {
        this.collaborator = collaborator;
    }


    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return LocalDateTime.now() +"Hello World!";
    }

    @RequestMapping("/injected")
    @ResponseBody
    public String injected(){
        return collaborator.message();
    }

}
