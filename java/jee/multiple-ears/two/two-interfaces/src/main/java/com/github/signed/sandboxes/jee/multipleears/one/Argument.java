package com.github.signed.sandboxes.jee.multipleears.one;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

public class Argument implements Serializable, Iterable<String>{

    private final List<String> messages = new ArrayList<>();
    private final DateTime now = new DateTime();

    public void add(String message){
        messages.add(message);
    }

    @Override
    public Iterator<String> iterator() {
        return messages.iterator();
    }
}
