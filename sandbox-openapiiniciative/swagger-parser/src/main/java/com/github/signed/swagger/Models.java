package com.github.signed.swagger;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import io.swagger.models.Model;
import io.swagger.models.properties.Property;

public class Models {
    public static Collection<Property> allProperties(Model model) {
        return Optional.ofNullable(model.getProperties()).orElse(Collections.emptyMap()).values();
    }
}
