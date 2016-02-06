package com.github.signed.changelog;

import static com.github.signed.changelog.IsPrefix.isAPrefixIn;
import static com.github.signed.changelog.Resources.readAsString;
import static java.nio.charset.Charset.forName;
import static java.time.LocalDate.of;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Test;

public class ReproduceSample_Test {

    private final ChangeLogBuilder changeLogBuilder = new ChangeLogBuilder();

    @Test
    public void serialized_changelog_matches_with_sample_from_homepage() throws Exception {
        changeLogBuilder.header()
                .withDescription("All notable changes to this project will be documented in this file.\nThis project adheres to [Semantic Versioning](http://semver.org/).");
        changeLogBuilder.version(unreleased()::build);
        changeLogBuilder.version(version_0_3_0()::build);

        ChangeLog changeLog = changeLogBuilder.build();

        MarkdownSerializer markdownSerializer = new MarkdownSerializer();
        changeLog.accept(markdownSerializer);

        String markdown = markdownSerializer.markdown();
        System.out.println(markdown);

        assertThat(markdown, isAPrefixIn(sample()));
    }

    public String sample() throws IOException {
        return readAsString("changelog.md", forName("UTF-8"));
    }

    private VersionBuilder unreleased() {
        VersionBuilder unreleased = new VersionBuilder().unreleased();
        unreleased.link(new Link("https://github.com/olivierlacan/keep-a-changelog/compare/v0.3.0...HEAD"));
        CategoryBuilder added = unreleased.added();
        added.item().text("zh-CN and zh-TW translations from @tianshuo.");
        added.item().text("de translation from @mpbzh.");
        return unreleased;
    }

    private VersionBuilder version_0_3_0() {
        VersionBuilder _0_3_0 = new VersionBuilder();

        _0_3_0.version(new VersionNumber(0, 3, 0))
                .releasedAt(new ReleaseDate(of(2015, 12, 3)))
                .link(new Link("https://github.com/olivierlacan/keep-a-changelog/compare/v0.2.0...v0.3.0"));
        CategoryBuilder added = _0_3_0.added();
        added.item().text("RU translation from @aishek.");
        added.item().text("pt-BR translation from @tallesl.");
        added.item().text("es-ES translation from @ZeliosAriex.");
        return _0_3_0;
    }

}
