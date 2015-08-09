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

        ErrorReportTO report = new ErrorReportTO();
        report.incident = "gamma alpha three";
        report.errors.add(error("not.what.i.expected", "my.litte.property", "I do not know the value yet"));
        report.errors.add(error("seriously", "2nd.path.down", "Value, I'll give you value!"));
        return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
    }

    private ErrorTO error(String code, String path, String value) {
        ErrorTO first = new ErrorTO();
        first.code = code;
        first.path = path;
        first.value = value;
        return first;
    }

}
