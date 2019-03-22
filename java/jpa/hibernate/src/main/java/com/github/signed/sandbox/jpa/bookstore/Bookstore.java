package com.github.signed.sandbox.jpa.bookstore;

import java.util.List;

import com.google.common.collect.Lists;

public class Bookstore {

    private Long id;
    private String name;
    private List<Book> books = Lists.newArrayList();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
