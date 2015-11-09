package com.github.signed.sanboxes.spring.advices;

public class BusinessException extends RuntimeException{
    public BusinessException() {
        super("Business Message");
    }
}
