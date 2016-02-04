package com.github.signed.changelog;

import java.util.List;

public class ChangeLog {

    private final Header header;
    private final List<Version> versions;

    public ChangeLog(Header header, List<Version> versions) {
        this.header = header;
        this.versions = versions;
    }

    public void accept(ChangelogVisitor visitor) {
        visitor.visit(header);
        versions.stream().forEachOrdered(visitor::visit);
    }
}
