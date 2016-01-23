package com.github.signed.swagger.validate;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.google.common.collect.Lists;

public class ValidationResultBuilder {

    private final List<ValidationMessageBuilder> messages = Lists.newArrayList();

    public void schemaValidationThrowAnExceptionFailed(Exception ex) {
        ex.printStackTrace();
    }

    public ValidationMessageBuilder message() {
        ValidationMessageBuilder builder = new ValidationMessageBuilder();
        messages.add(builder);
        return builder;
    }

    public ValidationResult build() {
        return new ValidationResult(messages.stream().map(ValidationMessageBuilder::build).collect(toList()));
    }
}
