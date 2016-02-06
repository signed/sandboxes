package com.github.signed.changelog;

public class Link {
    private final String url;

    public Link(String url) {
        this.url = url;
    }

    public String asString(){
        return url;
    }
}
