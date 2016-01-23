package com.github.signed.swagger.validate;

import static java.lang.String.format;

import java.io.PrintStream;

public class ValidationMessage {
    public final String level;
    public final String message;

    public ValidationMessage(String level, String message) {
        this.level = level;
        this.message = message;
    }

    public void writeTo(PrintStream out) {
        out.println(format("[%s] %s", level, this.message));
    }

    public static void writeTo(ValidationMessage validationMessage) {

    }
}
