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
        return category("Added");
    }

    public CategoryBuilder changed() {
        return category("Changed");
    }

    public CategoryBuilder deprecated() {
        return category("Deprecated");
    }

    public CategoryBuilder removed() {
        return category("Removed");
    }

    public CategoryBuilder fixed() {
        return category("Fixed");
    }

    public CategoryBuilder security() {
        return category("Security");
    }

    public CategoryBuilder category(String name) {
        CategoryBuilder categoryBuilder = new CategoryBuilder();
        categoryBuilder.name(name);
        categories.add(categoryBuilder);
        return categoryBuilder;
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
