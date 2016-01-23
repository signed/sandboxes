package com.github.signed.swagger.essentials;

import io.swagger.models.properties.RefProperty;

public class RefPropertyBuilder implements PropertyBuilder {
    private String reference;

    public RefPropertyBuilder withReferenceTo(String reference) {
        this.reference = reference;
        return this;
    }

    public RefProperty build() {
        return new RefProperty(reference);
    }
}
