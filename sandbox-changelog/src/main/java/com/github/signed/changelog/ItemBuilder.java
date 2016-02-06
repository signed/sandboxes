package com.github.signed.changelog;

import java.util.Arrays;
import java.util.List;

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
        String[] split = text.split("\n");

        List<String> lines = Arrays.asList(split);
        return new Item(lines);
    }
}
