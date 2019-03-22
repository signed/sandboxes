package com.github.signed.microplugin.core;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

public class ExtensionPoint<ExtensionInterface> {

    private final Multibinder<ExtensionInterface> multiBinder;

    protected ExtensionPoint(Binder binder, Class<ExtensionInterface> blueprint){
        multiBinder = Multibinder.newSetBinder(binder, blueprint);
    }

    public void theImplementer(Class<? extends ExtensionInterface> implementer) {
        multiBinder.addBinding().to(implementer);
    }
}
