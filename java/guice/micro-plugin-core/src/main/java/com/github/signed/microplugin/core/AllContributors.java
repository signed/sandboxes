package com.github.signed.microplugin.core;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.Set;

public class AllContributors<T> implements Iterable<T>{
    public Set<T> contributions;

    @Inject
    public AllContributors(Set<T> contributions) {
        this.contributions = contributions;
    }

    @Override
    public Iterator<T> iterator() {
        return contributions.iterator();
    }
}
