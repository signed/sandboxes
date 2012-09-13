package com.github.signed.microplugin.core;

import com.google.inject.AbstractModule;

public class GuicePluginRegistry  extends AbstractModule{

    @Override
    protected void configure() {
        binder();
    }
}
