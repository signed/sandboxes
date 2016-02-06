package com.github.signed.changelog;

public class ItemBuilder {

    public static ItemBuilder forText(String text){
        return new ItemBuilder().text(text);
    }

    private String text;

    public ItemBuilder text(String text) {
        this.text = text;
        return this;
    }

    public Item build() {
        return new Item(text);
    }
}
