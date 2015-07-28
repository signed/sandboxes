package com.github.signed.sandboxes.spring.beanvalidation;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ValidatingController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getHome() {
        BeanToValidate result = new BeanToValidate();
        result.name = "Hello World!";
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/primitives", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> putPrimitives(@Valid @RequestBody BeanToValidate bean) {
        return new ResponseEntity<>("Put done", HttpStatus.OK);
    }

    @RequestMapping(value = "/specialvalidator", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> putSpecialValidator(@Valid @RequestBody PhoneNumber phoneNumber) {
        return new ResponseEntity<>("Put done", HttpStatus.OK);
    }

}