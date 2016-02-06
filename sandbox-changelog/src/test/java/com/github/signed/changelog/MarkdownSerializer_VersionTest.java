package com.github.signed.changelog;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class MarkdownSerializer_VersionTest implements MarkdownSerializerFixture {

    private final VersionBuilder version = new VersionBuilder();

    @Test
    public void each_version_starts_with_a_level_two_heading() throws Exception {
        version.unreleased();

        assertThat(line(1), startsWith("## "));
    }

    @Test
    public void render_unreleased_in_headline() throws Exception {
        version.unreleased();

        assertThat(line(1), endsWith("Unreleased"));
    }

    @Test
    public void render_link_to_release() throws Exception {
        version.unreleased();
        version.link(new Link("https://example.org"));

        assertThat(line(1), endsWith("[Unreleased]"));
        assertThat(line(2), is(""));
        assertThat(secondToLastLine(), is("[Unreleased]: https://example.org"));
    }

    @Test
    public void add_newline_after_links() throws Exception {
        version.unreleased();
        version.link(new Link("https://example.org"));

        assertThat(lastLine(), is(""));
    }

    @Test
    public void if_present_render_the_version() throws Exception {
        version.version(new VersionNumber(4, 2, 1));

        assertThat(line(1), endsWith("4.2.1"));
    }

    @Test
    public void add_category_heading() throws Exception {
        version.added();

        assertThat(line(2), is("### Added"));
    }

    @Test
    public void add_all_items_of_a_category() throws Exception {
        CategoryBuilder fixed = version.fixed();
        fixed.item().text("one");
        fixed.item().text("two");

        assertThat(line(3), is("- one"));
        assertThat(line(4), is("- two"));
    }

    @Test
    public void only_a_single_empty_line_at_the_end() throws Exception {
        version.unreleased();

        assertThat(secondToLastLine(), not(equalTo("")));
    }

    @Override
    public MarkdownSerializer markdownSerializer() {
        MarkdownSerializer serializer = new MarkdownSerializer();
        serializer.visit(version.build());
        return serializer;
    }
}