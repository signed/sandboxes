package com.github.signed.sandboxes.jee.multipleears.one;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Argument implements Serializable, Iterable<String>{

    private final List<String> messages = new ArrayList<>();

    public void add(String message){
        messages.add(message);
    }

    @Override
    public Iterator<String> iterator() {
        return messages.iterator();
    }
}
