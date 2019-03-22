package com.github.signed.tryanderror.jersey.resources;

public interface Callback<T> {
    void call(T value);
}
