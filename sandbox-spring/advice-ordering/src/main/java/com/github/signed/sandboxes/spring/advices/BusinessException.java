package com.github.signed.sandboxes.spring.advices;

public class BusinessException extends RuntimeException{
    public BusinessException() {
        super("Business Message");
    }
}
