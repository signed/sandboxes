package com.github.signed.sandboxes.spring.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Order(Integer.MIN_VALUE)
public class ControllerAdviceForExceptionHandling{

    private final Reporter reporter;

    @Autowired
    public ControllerAdviceForExceptionHandling(Reporter reporter) {
        this.reporter = reporter;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<?> handleBusinessException() {
        reporter.earlierAdvise("BusinessException");
        return new ResponseEntity<>("MIN_VALUE", BAD_REQUEST);
    }
}
