package com.github.signed.protocols.jvm;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {

    private final MemoryDictionary dictionary;

    public Handler(MemoryDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    protected URLConnection openConnection(final URL url) throws IOException {
        InputStreamSource source;
        final String path = url.getHost();
        if (dictionary.contains(path)) {
            StringBuilder builder = dictionary.getByKey(path);
            source = new InputStreamFromStringBuilder(builder);
        }else {
            source = new FileNotFoundExceptionFromUrl(url);
        }

        return new MemoryDictionaryConnection(url, source);
    }

    private static class InputStreamFromStringBuilder implements InputStreamSource {
        private final StringBuilder builder;

        public InputStreamFromStringBuilder(StringBuilder builder) {
            this.builder = builder;
        }

        @Override
        public InputStream inputStream() throws FileNotFoundException {
            byte[] data = builder.toString().getBytes();
            return new ByteArrayInputStream(data);

        }
    }

    private static class FileNotFoundExceptionFromUrl implements InputStreamSource {
        private final URL url;

        public FileNotFoundExceptionFromUrl(URL url) {
            this.url = url;
        }

        @Override
        public InputStream inputStream() throws FileNotFoundException {
            throw new FileNotFoundException(url.toString());
        }
    }
}
