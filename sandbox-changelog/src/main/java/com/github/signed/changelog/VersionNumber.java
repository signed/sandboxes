package com.github.signed.changelog;

import static java.lang.String.format;

public class VersionNumber {
    private final int major;
    private final int minor;
    private final int micro;

    public VersionNumber(int major, int minor, int micro) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
    }

    public String asString() {
        return format("%d.%d.%d", major, minor, micro);
    }
}
