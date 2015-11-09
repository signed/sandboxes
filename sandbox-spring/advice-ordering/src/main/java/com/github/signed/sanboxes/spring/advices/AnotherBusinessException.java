package com.github.signed.sanboxes.spring.advices;

public class AnotherBusinessException extends RuntimeException {

    public AnotherBusinessException() {
        super("another business message");
    }
}
