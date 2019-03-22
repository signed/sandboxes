package com.github.signed.sandboxes.jee.data;

import org.joda.time.LocalDate;

public class LockMetadata {
    private final LocalDate day;

    public LockMetadata(LocalDate day) {
        this.day = day;
    }

    public LocalDate day() {
        return day;
    }
}
