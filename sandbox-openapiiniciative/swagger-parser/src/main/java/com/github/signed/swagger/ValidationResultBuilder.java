package com.github.signed.swagger;

import java.util.List;

import com.google.common.collect.Lists;

public class ValidationResultBuilder {

    private final List<String> messages = Lists.newArrayList();

    public ValidationResultBuilder addMessageFromSwaggerParser(String message){
        messages.add(message);
        return this;
    }

    public ValidationResult build(){
        return new ValidationResult(messages);
    }
}
