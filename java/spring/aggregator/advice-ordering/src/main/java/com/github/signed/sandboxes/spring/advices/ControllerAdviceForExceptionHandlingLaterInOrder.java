package com.github.signed.sandboxes.spring.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@ControllerAdvice
@Order(Integer.MAX_VALUE)
public class ControllerAdviceForExceptionHandlingLaterInOrder {

    private final Reporter reporter;

    @Autowired
    public ControllerAdviceForExceptionHandlingLaterInOrder(Reporter reporter) {
        this.reporter = reporter;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<String> handleBusinessException(){
        reporter.laterAdvise("BusinessException");
        return new ResponseEntity<>("MAX_VALUE", MOVED_PERMANENTLY);
    }

    @ExceptionHandler(AnotherBusinessException.class)
    @ResponseBody
    public ResponseEntity<String> handleAnotherBusinessException(){
        reporter.laterAdvise("AnotherBusinessException");
        return new ResponseEntity<>("MAX_VALUE", MOVED_PERMANENTLY);
    }
}
