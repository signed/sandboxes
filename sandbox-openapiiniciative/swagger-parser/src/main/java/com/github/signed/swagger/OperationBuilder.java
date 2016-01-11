package com.github.signed.swagger;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.models.Operation;

public class OperationBuilder {
    private final List<String> tags = newArrayList();

    public OperationBuilder withTag(String tag) {
        tags.add(tag);
        return this;
    }

    public Operation build() {
        Operation operation = new Operation();
        operation.setTags(Lists.newArrayList(tags));
        return operation;
    }
}
