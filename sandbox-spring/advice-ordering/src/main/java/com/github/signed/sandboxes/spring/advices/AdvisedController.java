package com.github.signed.sandboxes.spring.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvisedController {

    private final BusinessLogic businessLogic;
    private final Reporter reporter;

    @Autowired
    public AdvisedController(BusinessLogic businessLogic, Reporter reporter) {
        this.businessLogic = businessLogic;
        this.reporter = reporter;
    }

    @RequestMapping("/")
    @ResponseBody
    public String someData(@RequestBody String body) {
        //throw new AnotherBusinessException();
        //throw new BusinessException();
        reporter.controller();
        businessLogic.executeLogic();
        return "hello " + body;
    }

}
