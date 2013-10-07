package com.github.signed.sandbox.jpa;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

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
        String additionalArguments = "";
        if( !arguments.isEmpty()){
            additionalArguments = ";"+Joiner.on(";").join(arguments);

        }
        return "jdbc:h2:mem:" + databaseName+additionalArguments;
    }
}
