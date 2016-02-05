package com.github.signed.changelog;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Version implements Iterable<Category> {

    private final Optional<LocalDate> releaseDate;
    private final Optional<VersionNumber> versionNumber;
    private final List<Category> categories;

    public Version(Optional<LocalDate> releaseDate, Optional<VersionNumber> versionNumber, List<Category> categories) {
        this.releaseDate = releaseDate;
        this.versionNumber = versionNumber;
        this.categories = categories;
    }

    @Override
    public Iterator<Category> iterator() {
        return categories.iterator();
    }

    public Optional<LocalDate> releaseDate() {
        return releaseDate;
    }

    public Optional<VersionNumber> versionNumber(){
        return versionNumber;
    }
}
