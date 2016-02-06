package com.github.signed.changelog;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Version implements Iterable<Category> {

    private final Optional<ReleaseDate> releaseDate;
    private final Optional<VersionNumber> versionNumber;
    private final Optional<Link> link;
    private final List<Category> categories;

    public Version(Optional<ReleaseDate> releaseDate, Optional<VersionNumber> versionNumber, Optional<Link> link, List<Category> categories) {
        this.releaseDate = releaseDate;
        this.versionNumber = versionNumber;
        this.link = link;
        this.categories = categories;
    }

    @Override
    public Iterator<Category> iterator() {
        return categories.iterator();
    }

    public Optional<ReleaseDate> releaseDate() {
        return releaseDate;
    }

    public Optional<VersionNumber> versionNumber(){
        return versionNumber;
    }

    public Optional<Link> link(){
        return link;
    }
}
