package com.github.signed.protocols.jvm;

import java.net.URL;

public class InMemoryUrl {

    public static void registerInMemoryUrlHandler(MemoryDictionary dictionary) {
        URL.setURLStreamHandlerFactory(new StyleStreamHandlerFactory(dictionary));
    }
}
