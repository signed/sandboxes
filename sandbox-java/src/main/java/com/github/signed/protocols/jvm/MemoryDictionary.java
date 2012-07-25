package com.github.signed.protocols.jvm;

import java.util.HashMap;
import java.util.Map;

public class MemoryDictionary {

    private final Map<String, StringBuilder> keyToObject = new HashMap<String, StringBuilder>();

    public StringBuilder getByKey(String path) {
        StringBuilder stringBuilder = keyToObject.get(path);
        if (null == stringBuilder) {
            return new StringBuilder();
        }
        return stringBuilder;
    }

    public void depose(String key, StringBuilder builder) {
        keyToObject.put(key, builder);
    }
}
