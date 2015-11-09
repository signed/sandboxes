package com.github.signed.sanboxes.spring.advices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvisedController {


    @RequestMapping("/")
    @ResponseBody
    public String someData(){
        return "Hello World";
    }
}
