package org.example;

import com.google.common.base.Strings;

public class DocumentId {
    private final long id;

    public DocumentId(long id) {
        this.id = id;
    }

    public String asString() {
        return Strings.padStart(Long.toString(id), 20, '0');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentId that = (DocumentId) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
