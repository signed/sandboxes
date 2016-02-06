package com.github.signed.changelog;

import static com.github.signed.changelog.CategoryBuilder.changed;
import static com.github.signed.changelog.IsPrefix.isAPrefixIn;
import static com.github.signed.changelog.ItemBuilder.forText;
import static com.github.signed.changelog.Link.To;
import static com.github.signed.changelog.ReleaseDate.Date;
import static com.github.signed.changelog.Resources.readAsString;
import static com.github.signed.changelog.VersionNumber.SemVer;
import static java.nio.charset.Charset.forName;
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
        changeLogBuilder.version(_0_3_0()::build);
        changeLogBuilder.version(_0_2_0()::build);
        changeLogBuilder.version(_0_1_0()::build);

        ChangeLog changeLog = changeLogBuilder.build();

        MarkdownSerializer markdownSerializer = new MarkdownSerializer();
        changeLog.accept(markdownSerializer);

        String markdown = markdownSerializer.markdown();
        System.out.println(markdown);
        assertThat(markdown, isAPrefixIn(sample()));
    }

    private VersionBuilder _0_3_0() {
        VersionBuilder _0_3_0 = VersionBuilder.For(SemVer(0, 3, 0));
        _0_3_0.version(SemVer(0, 3, 0))
                .releasedAt(Date(2015, 12, 3))
                .link(To("https://github.com/olivierlacan/keep-a-changelog/compare/v0.2.0...v0.3.0"));
        CategoryBuilder added = _0_3_0.added();
        added.item().text("RU translation from @aishek.");
        added.item().text("pt-BR translation from @tallesl.");
        added.item().text("es-ES translation from @ZeliosAriex.");
        return _0_3_0;
    }

    private VersionBuilder _0_2_0() {
        VersionBuilder versionBuilder = VersionBuilder.For(SemVer(0, 2, 0));
        versionBuilder.releasedAt(Date(2015, 10, 6)).link(To("https://github.com/olivierlacan/keep-a-changelog/compare/v0.1.0...v0.2.0"));
        versionBuilder.changed().item().text("Remove exclusionary mentions of \"open source\" since this project can benefit\n" +
                "both \"open\" and \"closed\" source projects equally.");
        return versionBuilder;
    }

    private VersionBuilder _0_1_0() {
        VersionBuilder version = VersionBuilder.For(SemVer(0, 1, 0));
        version.link(Link.To("https://github.com/olivierlacan/keep-a-changelog/compare/v0.0.8...v0.1.0"));
        version.releasedAt(ReleaseDate.Date(2015, 10, 6));
        version.added().item().text("Answer \"Should you ever rewrite a change log?\".");
        version.category(changed()
                .item(forText("Improve argument against commit logs."))
                .item(forText("Start following [SemVer](http://semver.org) properly.")));
        return version;
    }

    public String sample() throws IOException {
        return readAsString("changelog.md", forName("UTF-8"));
    }

    private VersionBuilder unreleased() {
        VersionBuilder unreleased = new VersionBuilder().unreleased();
        unreleased.link(To("https://github.com/olivierlacan/keep-a-changelog/compare/v0.3.0...HEAD"));
        CategoryBuilder added = unreleased.added();
        added.item().text("zh-CN and zh-TW translations from @tianshuo.");
        added.item().text("de translation from @mpbzh.");
        return unreleased;
    }

}
