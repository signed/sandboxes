package com.github.signed.sandboxes.spring.advices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvisedController {

    @RequestMapping("/")
    @ResponseBody
    public String someData(){
        //throw new AnotherBusinessException();
        //throw new BusinessException();
        return "Hello World";
    }
}
