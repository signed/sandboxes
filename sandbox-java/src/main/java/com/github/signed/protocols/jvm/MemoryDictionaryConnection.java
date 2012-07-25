package com.github.signed.protocols.jvm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class MemoryDictionaryConnection extends URLConnection {

    private final StringBuilder builder;

    public MemoryDictionaryConnection(URL url, StringBuilder builder) {
        super(url);
        this.builder = builder;
    }

    @Override
    public void connect() throws IOException {
        //nothing to do...
    }

    @Override
    public InputStream getInputStream() throws IOException {
        byte[] data = builder.toString().getBytes();
        return new ByteArrayInputStream(data);
    }
}
