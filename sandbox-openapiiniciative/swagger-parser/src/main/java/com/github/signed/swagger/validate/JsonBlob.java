package com.github.signed.swagger.validate;

public class JsonBlob {
    private final String json;

    public JsonBlob(String json) {
        this.json = json;
    }

    public String asString() {
        return json;
    }
}
