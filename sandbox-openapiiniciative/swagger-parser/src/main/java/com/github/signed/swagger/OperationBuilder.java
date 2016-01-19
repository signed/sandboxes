package com.github.signed.swagger;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.models.Operation;

public class OperationBuilder {
    private final List<String> tags = newArrayList();
    private final List<ParameterBuilder> parameters = Lists.newArrayList();

    public OperationBuilder withTag(String tag) {
        tags.add(tag);
        return this;
    }

    public ParameterBuilder withParameter() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        withParameter(parameterBuilder);
        return parameterBuilder;
    }

    public OperationBuilder withParameter(ParameterBuilder parameterBuilder) {
        parameters.add(parameterBuilder);
        return this;
    }

    public Operation build() {
        Operation operation = new Operation();
        if (!tags.isEmpty()) {
            operation.setTags(Lists.newArrayList(tags));
        }
        parameters.forEach(parameterBuilder -> operation.addParameter(parameterBuilder.build()));
        return operation;
    }
}
