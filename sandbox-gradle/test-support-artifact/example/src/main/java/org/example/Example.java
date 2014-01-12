package org.example;

import org.joda.time.DateTime;

public class Example {

    private final DateTime created;
    private final String name;
    private final String description;


    public Example(DateTime created, String name, String description) {
        this.created = created;
        this.name = name;
        this.description = description;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return this.name + "("+created +")\n"+ description;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Example)){
            return false;
        }
        Example that = (Example) obj;
        return this.name.equals(that.name);
    }
}
