package com.github.signed.changelog;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

public class ChangeLogBuilder {

    private final HeaderBuilder header = new HeaderBuilder();
    private final List<VersionBuilder> versions = new ArrayList<>();

    public HeaderBuilder header() {
        return header;
    }

    public ChangeLog build() {
        return new ChangeLog(header.build(), versions.stream().map(VersionBuilder::build).collect(toList()));
    }
}
