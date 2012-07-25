package com.github.signed.protocols.jvm;

import java.util.HashMap;
import java.util.Map;

public class MemoryDictionary {

    private final Map<String, StringBuilder> keyToObject = new HashMap<String, StringBuilder>();

    public void depose(String key, StringBuilder builder) {
        keyToObject.put(key, builder);
    }

    public StringBuilder getByKey(String key) {
        if(!contains(key)){
            throw new RuntimeException("not value for key " + key);
        }
        return keyToObject.get(key);
    }

    public boolean contains(String key) {
        return keyToObject.containsKey(key);
    }
}
