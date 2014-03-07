package com.github.signed.sandboxes.jee.multipleears.one;

import javax.ejb.Stateless;

@Stateless
public class OnlyTwo implements Two {

    @Override
    public void dumpToSystemOut() {
        System.out.println("I am the mighty two");
    }

    @Override
    public void executeWith(Argument argument) {
        for (String message : argument) {
            System.out.println(message);
        }
    }

    @Override
    public Doom access() {
        return new Y2k();
    }
}