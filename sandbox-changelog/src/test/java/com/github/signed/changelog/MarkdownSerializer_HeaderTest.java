package com.github.signed.changelog;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.junit.Test;

public class MarkdownSerializer_HeaderTest implements MarkdownSerializerFixture {
    private final Header header = new Header("hello world");
    private final MarkdownSerializer markdownSerializer = new MarkdownSerializer();

    @Test
    public void write_heading_stating_the_purpose_of_the_document() throws Exception {
        assertThat(line(1), startsWith("# Change Log"));
    }

    @Test
    public void write_write_header_description_in_next_line() throws Exception {
        assertThat(line(2), equalTo("hello world"));
    }

    @Test
    public void write_newline_after_description() throws Exception {
        assertThat(lastLine(), equalTo(""));
    }

    @Override
    public MarkdownSerializer markdownSerializer() {
        markdownSerializer.visit(header);
        return markdownSerializer;
    }
}