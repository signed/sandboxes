package org.example;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Example> examples = new ArrayList<>();

    public void add(Example example) {
        examples.add(example);
    }

    public boolean contains(Example rain) {
        return true;
    }
}
