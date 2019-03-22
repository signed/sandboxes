package com.github.signed.tryanderror.jersey.resources;

import com.google.inject.Module;

public interface GuiceResourceProvider {
    public Module getResourceModule();
}