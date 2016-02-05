package com.github.signed.changelog;

import java.util.Iterator;
import java.util.List;

public class Category implements Iterable<Item>{
    private final String name;
    private final List<Item> items;

    public Category(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public String name() {
        return name;
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
