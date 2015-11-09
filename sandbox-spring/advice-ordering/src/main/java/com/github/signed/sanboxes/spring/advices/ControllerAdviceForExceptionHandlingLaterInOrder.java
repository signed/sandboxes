package com.github.signed.sanboxes.spring.advices;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Integer.MAX_VALUE)
public class ControllerAdviceForExceptionHandlingLaterInOrder {

    @ExceptionHandler(BusinessException.class)
    public void handleBusinessException(){
        System.out.println("the later one");
    }

    @ExceptionHandler(AnotherBusinessException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Order")
    public void handleAnotherBusinessException(){
        System.out.println("the only one handling another business exception");
    }
}
