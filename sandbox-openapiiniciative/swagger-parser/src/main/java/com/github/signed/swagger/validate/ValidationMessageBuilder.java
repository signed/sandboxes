package com.github.signed.swagger.validate;

public class ValidationMessageBuilder {

    private String level;
    private String message;

    public ValidationMessageBuilder level(String level) {
        this.level = level;
        return this;
    }

    public ValidationMessageBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ValidationMessage build(){
        return new ValidationMessage(level, message);
    }
}
