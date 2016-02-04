package com.github.signed.changelog;

public class HeaderBuilder {
    private String text;

    public HeaderBuilder withDescription(String text){
        this.text = text;
        return this;
    }

    public Header build() {
        return new Header(text);
    }
}
