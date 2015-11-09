package com.github.signed.sanboxes.spring.advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceForExceptionHandling {

    @ExceptionHandler(BusinessException.class)
    public void handleBusinessException(){
        System.out.println("handle business exception");
    }
}
