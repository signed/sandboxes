package com.github.signed.changelog;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.List;

import org.junit.Test;

public class MarkdownSerializer_HeaderTest {
    private final MarkdownSerializer markdownSerializer = new MarkdownSerializer();
    private final Header header = new Header("hello world");

    @Test
    public void write_heading_stating_the_purpose_of_the_document() throws Exception {
        assertThat(markdownLines().get(0), startsWith("# Change Log"));
    }

    @Test
    public void write_write_header_description_in_next_line() throws Exception {
        assertThat(markdownLines().get(1), equalTo("hello world"));
    }

    @Test
    public void write_newline_after_description() throws Exception {
        assertThat(markdownLines().get(2), equalTo(""));
    }

    private List<String> markdownLines() {
        markdownSerializer.visit(header);
        return asList(markdownSerializer.markdown().split("\n", -1));
    }

}