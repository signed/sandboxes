package com.github.signed.changelog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryBuilder {

    public static CategoryBuilder added() {
        return named("Added");
    }

    public static CategoryBuilder changed() {
        return named("Changed");
    }

    public static CategoryBuilder deprecated() {
        return named("Deprecated");
    }

    public static CategoryBuilder removed() {
        return named("Removed");
    }

    public static CategoryBuilder fixed() {
        return named("Fixed");
    }

    public static CategoryBuilder security() {
        return named("Security");
    }

    public static CategoryBuilder named(String name) {
        CategoryBuilder categoryBuilder = new CategoryBuilder();
        categoryBuilder.name(name);
        return categoryBuilder;
    }

    private final List<ItemBuilder> items = new ArrayList<>();

    private String name;

    public CategoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder item() {
        ItemBuilder itemBuilder = new ItemBuilder();
        item(itemBuilder);
        return itemBuilder;
    }

    public CategoryBuilder item(ItemBuilder itemBuilder) {
        items.add(itemBuilder);
        return this;
    }

    public Category build() {
        return new Category(name, items.stream().map(ItemBuilder::build).collect(Collectors.toList()));
    }
}
