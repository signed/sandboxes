package com.github.signed.changelog;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

public class GoldMaster_Test {

    private final ChangeLogBuilder changeLogBuilder = new ChangeLogBuilder();

    @Test
    @Ignore("that is the short term goal to work towards")
    public void name() throws Exception {
        changeLogBuilder.header().withDescription("All notable changes to this project will be documented in this file.\n" +
                "This project adheres to [Semantic Versioning](http://semver.org/).");
        VersionBuilder versionBuilder = changeLogBuilder.version().unreleased();
        CategoryBuilder added = versionBuilder.added();
        added.item().text("zh-CN and zh-TW translations from @tianshuo.");
        added.item().text("de translation from @mpbzh.");
        ChangeLog changeLog = changeLogBuilder.build();

        MarkdownSerializer markdownSerializer = new MarkdownSerializer();
        changeLog.accept(markdownSerializer);


        Stream<String> stream = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("changelog.md"))).lines();
        String goldMaster = stream.reduce("", (s, s2) -> s + "\n" + s2);

        String markdown = markdownSerializer.markdown();
        System.out.println(markdown);
        assertThat(markdown, Matchers.equalTo(goldMaster));
    }
}
