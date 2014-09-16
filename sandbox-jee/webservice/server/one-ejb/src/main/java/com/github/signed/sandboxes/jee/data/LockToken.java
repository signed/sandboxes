package com.github.signed.sandboxes.jee.data;

public class LockToken {
    private final String tokenString;

    public LockToken(String tokenString) {
        this.tokenString = tokenString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockToken lockToken = (LockToken) o;

        return tokenString.equals(lockToken.tokenString);
    }

    @Override
    public int hashCode() {
        return tokenString.hashCode();
    }
}
