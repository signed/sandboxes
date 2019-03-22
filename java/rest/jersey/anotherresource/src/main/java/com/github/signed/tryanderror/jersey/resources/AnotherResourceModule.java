package com.github.signed.tryanderror.jersey.resources;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

public class AnotherResourceModule extends AbstractModule implements GuiceResourceProvider{
    @Override
    protected void configure() {
        bind(AnotherResource.class);
    }

    @Override
    public Module getResourceModule() {
        return new AnotherResourceModule();
    }
}