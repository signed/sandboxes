package com.github.signed.changelog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VersionBuilder {

    public static VersionBuilder For(VersionNumber versionNumber) {
        return new VersionBuilder().version(versionNumber);
    }

    private final List<CategoryBuilder> categories = new ArrayList<>();
    private Optional<ReleaseDate> maybeReleaseDate = Optional.empty();
    private Optional<VersionNumber> maybeVersionNumber = Optional.empty();

    private Optional<Link> maybeLink = Optional.empty();

    public VersionBuilder unreleased() {
        maybeReleaseDate = Optional.empty();
        maybeVersionNumber = Optional.empty();
        return this;
    }

    public CategoryBuilder added() {
        CategoryBuilder added = CategoryBuilder.added();
        category(added);
        return added;
    }

    public CategoryBuilder changed() {
        CategoryBuilder changed = CategoryBuilder.changed();
        category(changed);
        return changed;
    }

    public CategoryBuilder deprecated() {
        CategoryBuilder deprecated = CategoryBuilder.deprecated();
        category(deprecated);
        return deprecated;
    }

    public CategoryBuilder removed() {
        CategoryBuilder removed = CategoryBuilder.removed();
        category(removed);
        return removed;
    }

    public CategoryBuilder fixed() {
        CategoryBuilder fixed = CategoryBuilder.fixed();
        category(fixed);
        return fixed;
    }

    public CategoryBuilder security() {
        CategoryBuilder security = CategoryBuilder.security();
        category(security);
        return security;
    }

    public VersionBuilder category(CategoryBuilder categoryBuilder){
        categories.add(categoryBuilder);
        return this;
    }

    public VersionBuilder releasedAt(ReleaseDate releaseDate) {
        this.maybeReleaseDate = Optional.of(releaseDate);
        return this;
    }

    public VersionBuilder version(VersionNumber versionNumber){
        this.maybeVersionNumber = Optional.of(versionNumber);
        return this;
    }

    public VersionBuilder link(Link link) {
        maybeLink = Optional.of(link);
        return this;
    }

    public Version build() {
        return new Version(maybeReleaseDate, maybeVersionNumber, maybeLink, categories.stream().map(CategoryBuilder::build).collect(Collectors.toList()));
    }
}
