package com.github.signed.swagger;

import java.io.PrintStream;
import java.util.List;

public class ValidationResult {

    private final List<String> messages;

    public ValidationResult(List<String> messages) {
        this.messages = messages;
    }

    public void writeTo(PrintStream out) {
        messages.forEach(out::println);
    }

    public boolean hasPassed() {
        return messages.isEmpty();
    }

    public boolean failed() {
        return !hasPassed();
    }
}
