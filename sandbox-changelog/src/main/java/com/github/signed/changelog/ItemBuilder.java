package com.github.signed.changelog;

public class ItemBuilder {
    private String text;

    public void text(String text) {
        this.text = text;
    }

    public Item build() {
        return new Item(text);
    }
}
