package com.github.signed.microplugin.core;

import com.github.signed.microplugin.core.ApplicationContributionModule;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public abstract  class ModuleWithContributionSupport extends AbstractModule implements ApplicationContributionModule {

    protected <ExtensionPoint>void contributeTo(Class<ExtensionPoint> extensionPointClass, Class<? extends ExtensionPoint> implementer){
        Multibinder<ExtensionPoint> toolBarContributionMultibinder = Multibinder.newSetBinder(binder(), extensionPointClass);
        toolBarContributionMultibinder.addBinding().to(implementer);
    }
}
