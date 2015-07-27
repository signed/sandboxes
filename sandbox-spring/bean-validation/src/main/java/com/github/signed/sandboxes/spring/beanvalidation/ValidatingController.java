package com.github.signed.sandboxes.spring.beanvalidation;

import javax.validation.Valid;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
public class ValidatingController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public BeanToValidate getHome() {
        BeanToValidate result = new BeanToValidate();
        result.name = "Hello World!";
        return result;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public String putHome(@Valid @RequestBody BeanToValidate bean) {
        System.out.println(bean);
        return "Put done";
    }

}