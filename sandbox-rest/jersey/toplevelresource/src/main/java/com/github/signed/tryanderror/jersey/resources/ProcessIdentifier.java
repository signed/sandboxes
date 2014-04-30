package com.github.signed.tryanderror.jersey.resources;

import com.google.common.base.Objects;

public class ProcessIdentifier {
    private final long identifier;

    public ProcessIdentifier(long identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return String.valueOf(identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier);
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof ProcessIdentifier)) {
            return false;
        }
        ProcessIdentifier other = (ProcessIdentifier) that;
        return Objects.equal(identifier, other.identifier);
    }
}
