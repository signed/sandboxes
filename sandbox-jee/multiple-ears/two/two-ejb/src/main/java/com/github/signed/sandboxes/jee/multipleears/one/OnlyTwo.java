package com.github.signed.sandboxes.jee.multipleears.one;

import javax.ejb.Stateless;

@Stateless
public class OnlyTwo implements Two {

    @Override
    public void dumpToSystemOut() {
        System.out.println("I am the might two");
    }
}