package com.github.signed.changelog;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarkdownSerializer implements ChangelogVisitor {

    private static class RenderedLink {
        private final String name;
        private final Link link;

        private RenderedLink(String name, Link link) {
            this.name = name;
            this.link = link;
        }
    }

    private final List<String> lines = new ArrayList<>();

    @Override
    public void visit(Header header) {
        lines.add("# Change Log");
        lines.add(header.text());
        lines.add("");
    }

    @Override
    public void visit(Version version) {
        List<RenderedLink> renderedLinks = new ArrayList<>();

        String versionString = version.versionNumber().map(VersionNumber::asString).orElse("Unreleased");
        if (version.link().isPresent()) {
            lines.add(format("## [%s]", versionString));
            renderedLinks.add(new RenderedLink(versionString, version.link().get()));
        } else {
            lines.add(format("## %s", versionString));
        }

        for (Category category : version) {
            lines.add("### " + category.name());
            for (Item item : category) {
                lines.add("- " + item.text());
            }
        }
        if (!renderedLinks.isEmpty()) {
            lines.add("");
            renderedLinks.stream()
                    .map(renderedLink -> format("[%s]: %s", renderedLink.name, renderedLink.link.asString()))
                    .forEachOrdered(lines::add);
        }
        lines.add("");
    }

    public String markdown() {
        return lines.stream().collect(Collectors.joining("\n"));
    }
}
