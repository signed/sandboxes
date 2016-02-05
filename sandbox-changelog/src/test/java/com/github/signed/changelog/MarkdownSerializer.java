package com.github.signed.changelog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarkdownSerializer implements ChangelogVisitor {

    private final List<String> lines = new ArrayList<>();

    @Override
    public void visit(Header header) {
        lines.add("# Change Log");
        lines.add(header.text());
        lines.add("");
    }

    @Override
    public void visit(Version version) {
        String versionAsText = version.versionNumber().map(VersionNumber::asString).orElse("[Unreleased]");
        lines.add("## " + versionAsText);
        for (Category category : version) {
            lines.add("### " + category.name());
            for (Item item : category) {
                lines.add("- " + item.text());
            }
        }
    }


    public String markdown() {
        return lines.stream().collect(Collectors.joining("\n"));
    }
}
