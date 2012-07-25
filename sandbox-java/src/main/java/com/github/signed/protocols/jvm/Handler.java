package com.github.signed.protocols.jvm;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler{

    private final MemoryDictionary customHandler;

    public Handler(MemoryDictionary customHandler) {
        this.customHandler = customHandler;
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new MemoryDictionaryConnection(url, customHandler);
    }
}
