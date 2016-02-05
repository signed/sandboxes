package com.github.signed.changelog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryBuilder {
    private final List<ItemBuilder> items = new ArrayList<>();
    private String name;

    public CategoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder item() {
        ItemBuilder itemBuilder = new ItemBuilder();
        items.add(itemBuilder);
        return itemBuilder;
    }

    public Category build() {
        return new Category(name, items.stream().map(ItemBuilder::build).collect(Collectors.toList()));
    }
}
