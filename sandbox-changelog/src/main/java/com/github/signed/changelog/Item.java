package com.github.signed.changelog;

import java.util.List;

public class Item {
    private final List<String> lines;

    public Item(List<String> lines) {
        this.lines = lines;
    }

    public List<String> lines(){
        return lines;
    }

}
