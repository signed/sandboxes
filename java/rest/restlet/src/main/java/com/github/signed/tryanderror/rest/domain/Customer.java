package com.github.signed.tryanderror.rest.domain;

public class Customer {
    public final String name;

    public Customer(String name) {
        this.name = name;
    }

    public static Customer createSample() {
        return new Customer("Sample");
    }
}