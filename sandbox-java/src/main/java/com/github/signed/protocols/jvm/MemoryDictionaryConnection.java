package com.github.signed.protocols.jvm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class MemoryDictionaryConnection extends URLConnection {

    private final InputStreamSource source;

    public MemoryDictionaryConnection(URL url, InputStreamSource source) {
        super(url);
        this.source = source;
    }

    @Override
    public void connect() throws IOException {
        //nothing to do...
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return source.inputStream();
    }
}
