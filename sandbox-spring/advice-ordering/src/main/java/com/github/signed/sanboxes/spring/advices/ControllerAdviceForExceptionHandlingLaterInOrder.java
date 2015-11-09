package com.github.signed.sanboxes.spring.advices;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Integer.MAX_VALUE)
public class ControllerAdviceForExceptionHandlingLaterInOrder {

    @ExceptionHandler(BusinessException.class)
    public void handleBusinessException(){
        System.out.println("the later one");
    }

    @ExceptionHandler(AnotherBusinessException.class)
    public void handleAnotherBusinessException(){
        System.out.println("the only one handling another business exception");
    }
}
