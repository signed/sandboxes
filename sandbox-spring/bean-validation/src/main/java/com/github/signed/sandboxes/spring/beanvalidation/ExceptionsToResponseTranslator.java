package com.github.signed.sandboxes.spring.beanvalidation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsToResponseTranslator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBeansMarkedAsInvalidByBeanValidation(HttpServletRequest request, MethodArgumentNotValidException exception) {
        //look into the errors from the bean validation and provide proper feedback
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
