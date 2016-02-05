package com.github.signed.changelog;

public class Item {
    private final String text;

    public Item(String text) {
        this.text = text;
    }

    public final String text(){
        return text;
    }

}
