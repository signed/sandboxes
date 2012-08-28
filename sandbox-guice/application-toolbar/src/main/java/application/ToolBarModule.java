package application;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import contributions.ApplicationContributionModule;

public class ToolBarModule extends AbstractModule implements ApplicationContributionModule{
    @Override
    protected void configure() {
        bind(ApplicationModel.class).in(Singleton.class);
    }

    @Override
    public Module instance() {
        return this;
    }
}
