package com.github.signed.changelog;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ChangeLogBuilder {

    private final HeaderBuilder header = new HeaderBuilder();
    private final List<Supplier<Version>> versions = new ArrayList<>();

    public HeaderBuilder header() {
        return header;
    }

    public VersionBuilder version() {
        VersionBuilder versionBuilder = new VersionBuilder();
        version(versionBuilder::build);
        return versionBuilder;
    }

    public ChangeLogBuilder version(Supplier<Version> versionSupplier) {
        versions.add(versionSupplier);
        return this;
    }

    public ChangeLog build() {
        return new ChangeLog(header.build(), versions.stream().map(Supplier::get).collect(toList()));
    }
}
