package com.github.signed.sandboxes.spring.advices;

public class AnotherBusinessException extends RuntimeException {

    public AnotherBusinessException() {
        super("another business message");
    }
}
