package com.github.signed.integration.camel;

import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;

public class Parameters {
    private final Map<String, String> options = Maps.newHashMap();

    public void put(String knownHostsFile, String s) {
        options.put(knownHostsFile, s);
    }

    public String toArgumentString(){
        return FluentIterable.from(options.entrySet()).transform(new Function<Map.Entry<String, String>, String>() {
            @Override
            public String apply(Map.Entry<String, String> input) {
                return input.getKey() + "=" + input.getValue();
            }
        }).join(Joiner.on("&"));
    }
}
