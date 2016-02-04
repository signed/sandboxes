package com.github.signed.changelog;

public interface ChangelogVisitor {
    void visit(Header header);

    void visit(Version version);
}
