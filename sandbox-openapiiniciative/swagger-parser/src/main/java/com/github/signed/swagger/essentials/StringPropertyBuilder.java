package com.github.signed.swagger.essentials;

import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;

public class StringPropertyBuilder implements PropertyBuilder {
    private String format;

    public StringPropertyBuilder withFormat(String format) {
        this.format = format;
        return this;
    }

    public Property build() {
        StringProperty property = new StringProperty();
        property.setFormat(format);
        return property;
    }
}
