package com.github.signed.swagger;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import io.swagger.models.Operation;
import io.swagger.models.Path;

public class PathBuilder {
    private List<String> tags = newArrayList();

    public PathBuilder withTag(String tag){
        tags.add(tag);
        return this;
    }

    public Path build(){
        Operation operation = new Operation();
        operation.setTags(newArrayList(tags));
        Path path = new Path();
        path.setOptions(operation);
        return path;
    }
}
