package com.github.signed.changelog;

import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

import java.util.List;

public interface MarkdownSerializerFixture {

    default String lastLine() {
        List<String> lines = markdownLines();
        return lines.get(lines.size() - 1);
    }

    default String line(int lineNumber) {
        List<String> strings = markdownLines();
        if (lineNumber > strings.size()) {
            fail("There is no line " + lineNumber + "\n\n" + markdown());
        }
        return strings.get(lineNumber - 1);
    }

    default List<String> markdownLines() {
        return asList(markdown().split("\n", -1));
    }

    default String markdown() {
        return markdownSerializer().markdown();
    }

    MarkdownSerializer markdownSerializer();

}
