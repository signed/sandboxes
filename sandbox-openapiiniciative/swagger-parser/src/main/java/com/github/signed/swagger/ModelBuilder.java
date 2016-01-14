package com.github.signed.swagger;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;

public class ModelBuilder {
    private String type;

    public ModelBuilder withTypeObject(){
        type = "object";
        return this;
    }

    public ModelBuilder withTypeString() {
        type = "String";
        return this;
    }

    public Model build() {
        ModelImpl model = new ModelImpl();
        model.setType(type);
        return model;
    }
}
