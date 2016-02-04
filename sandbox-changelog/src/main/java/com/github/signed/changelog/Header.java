package com.github.signed.changelog;

public class Header {

    private final String text;

    public Header(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }
}
