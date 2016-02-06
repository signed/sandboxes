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

        String versionAsPlainString = version.versionNumber().map(VersionNumber::asString).orElse("Unreleased");
        String releaseDatePartOfHeadlineString = version.releaseDate().map((releaseDate) -> " - " + releaseDate.asString()).orElse("");
        String versionPartOfHeadline;
        if (version.link().isPresent()) {
            versionPartOfHeadline = format("[%s]", versionAsPlainString);
            renderedLinks.add(new RenderedLink(versionAsPlainString, version.link().get()));
        } else {
            versionPartOfHeadline = versionAsPlainString;
        }
        lines.add(format("## %s%s", versionPartOfHeadline, releaseDatePartOfHeadlineString));

        boolean remove = false;
        for (Category category : version) {
            lines.add("### " + category.name());
            for (Item item : category) {
                lines.add("- " +String.join("\n  ",item.lines()));
            }
            lines.add("");
            remove = true;
        }

        if (remove) {
            lines.remove(lines.size() - 1);
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
