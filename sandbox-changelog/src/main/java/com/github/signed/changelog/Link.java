package com.github.signed.changelog;

public class Link {

    public static Link To(String url) {
        return new Link(url);
    }

    private final String url;

    public Link(String url) {
        this.url = url;
    }

    public String asString(){
        return url;
    }
}
