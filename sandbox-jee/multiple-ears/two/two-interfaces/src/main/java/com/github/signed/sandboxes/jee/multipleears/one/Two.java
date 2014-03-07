package com.github.signed.sandboxes.jee.multipleears.one;

import javax.ejb.Remote;

@Remote
public interface Two {

    void dumpToSystemOut();

    void executeWith(Argument argument);

    Doom access();
}
