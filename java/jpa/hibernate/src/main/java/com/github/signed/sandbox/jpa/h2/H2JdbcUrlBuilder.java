package com.github.signed.sandbox.jpa.h2;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class H2JdbcUrlBuilder {

    private String databaseName = "test";

    private final List<String> arguments = Lists.newArrayList();

    public H2JdbcUrlBuilder keepDataInMemoryUntilJvmShutdown() {
        arguments.add("DB_CLOSE_DELAY=-1");
        return this;
    }

    public H2JdbcUrlBuilder database(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public String buildUrl() {
        String additionalArguments = Joiner.on(";").join(arguments);
        return String.format("jdbc:h2:mem:%s;%s", databaseName, additionalArguments);
    }
}