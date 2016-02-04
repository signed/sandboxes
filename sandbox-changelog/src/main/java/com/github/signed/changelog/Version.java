package com.github.signed.changelog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Version implements Iterable<Item> {
    private final List<Item> items = new ArrayList<>();


    public Optional<Link> link() {
        return Optional.of(new Link());
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    public Optional<ReleaseDate> releaseDate() {
        return Optional.empty();
    }
}
