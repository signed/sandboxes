package com.github.signed.swagger;

import java.util.Optional;

import io.swagger.models.Info;

public class InfoBuilder {
    private Optional<String> maybeVersion = Optional.empty();
    private Optional<String> maybeTitle = Optional.empty();

    public InfoBuilder withVersion(String version) {
        maybeVersion = Optional.of(version);
        return this;
    }

    public InfoBuilder withTitle(String title){
        maybeTitle = Optional.of(title);
        return this;
    }

    public Info build(){
        Info info = new Info();
        info.setVersion(maybeVersion.get());
        info.setTitle(maybeTitle.get());
        return info;
    }
}
