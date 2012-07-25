package com.github.signed.protocols.jvm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

class MemoryDictionaryConnection extends URLConnection {

    private MemoryDictionary dictionary;

    public MemoryDictionaryConnection(URL url, MemoryDictionary dictionary) {
        super(url);
        this.dictionary = dictionary;
    }

    @Override
    public void connect() throws IOException {
        //nothing to do...
    }

    @Override
    public InputStream getInputStream() throws IOException {
        String path = url.getHost();
        if(!dictionary.contains(path)) {
            throw new ConnectException("Connection refused");
        }
        StringBuilder builder = dictionary.getByKey(path);
        byte[] data = builder.toString().getBytes();
        return new ByteArrayInputStream(data);
    }
}
