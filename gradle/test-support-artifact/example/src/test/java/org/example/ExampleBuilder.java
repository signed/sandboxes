package org.example;

import org.joda.time.DateTime;

public class ExampleBuilder {
    public static ExampleBuilder name(String name) {
        return new ExampleBuilder().example(name);
    }


    private DateTime created;
    private String description;

    private String name;

    public ExampleBuilder example(String name) {
        this.name = name;
        return this;
    }

    public ExampleBuilder contains(String description) {
        this.description = description;
        return this;
    }

    public ExampleBuilder createdToday() {
        created = new DateTime();
        return this;
    }

    public Example build() {
        return new Example(created, name, description);
    }
}
