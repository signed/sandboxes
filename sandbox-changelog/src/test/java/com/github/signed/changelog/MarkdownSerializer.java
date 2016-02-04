package com.github.signed.changelog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MarkdownSerializer implements ChangelogVisitor {

    private final List<String> lines = new ArrayList<>();

    @Override
    public void visit(Header header) {
        lines.add("# Change Log");
        lines.add(header.text());
        lines.add("");
    }

    @Override
    public void visit(Version version) {

        List<Optional<Link>> links = new ArrayList<>();

        links.add(version.link());
        Optional<ReleaseDate> maybeReleaseDate = version.releaseDate();

        lines.add("## ");

        for(Item item: version){
            Optional<Link> maybeLink = item.link();
            links.add(maybeLink);
        }




    }


    public String markdown(){
        return lines.stream().collect(Collectors.joining("\n"));
    }
}
