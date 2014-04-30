package com.github.signed.tryanderror.rest.domain;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name;
    public int age;

    public Person(final String name, final int age) {
        super();
        this.name = name;
        this.age = age;
    }

}
