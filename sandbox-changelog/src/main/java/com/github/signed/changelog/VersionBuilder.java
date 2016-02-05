package com.github.signed.changelog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VersionBuilder {
    private Optional<LocalDate> releaseDate = Optional.empty();
    private Optional<VersionNumber> versionNumber = Optional.empty();

    private final List<CategoryBuilder> categories = new ArrayList<>();

    public VersionBuilder unreleased() {
        releaseDate = Optional.empty();
        versionNumber = Optional.empty();
        return this;
    }

    public CategoryBuilder added() {
        CategoryBuilder categoryBuilder = new CategoryBuilder();
        categoryBuilder.name("Added");
        categories.add(categoryBuilder);
        return categoryBuilder;
    }

    public Version build(){
        return new Version(releaseDate, versionNumber, categories.stream().map(CategoryBuilder::build).collect(Collectors.toList()));
    }
}
