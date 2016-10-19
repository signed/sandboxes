package com.github.signed.sandboxes.spring.advices;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Integer.MIN_VALUE)
public class ControllerAdviceForExceptionHandling{

    @ExceptionHandler(BusinessException.class)
    public void handleBusinessException(){
        System.out.println("handle business exception");
    }
}
