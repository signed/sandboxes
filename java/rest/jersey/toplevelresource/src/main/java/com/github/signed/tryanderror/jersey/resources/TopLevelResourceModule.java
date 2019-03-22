package com.github.signed.tryanderror.jersey.resources;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class TopLevelResourceModule extends AbstractModule implements GuiceResourceProvider{

    @Override
    protected void configure() {
        bind(TopLevelResource.class);
        bind(LongRunningProcessResource.class);
        bind(ProcessArchive.class).in(Scopes.SINGLETON);
    }

    @Override
    public Module getResourceModule() {
        return new TopLevelResourceModule();
    }
}