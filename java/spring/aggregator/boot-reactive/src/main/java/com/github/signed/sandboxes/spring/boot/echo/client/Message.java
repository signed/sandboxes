package com.github.signed.sandboxes.spring.boot.echo.client;

public class Message {

    public static Message WithText(String first) {
        return new Message(first);
    }

    private final String message;

    public Message(String message) {
        this.message = message;
    }

    public String text() {
        return message;
    }
}
