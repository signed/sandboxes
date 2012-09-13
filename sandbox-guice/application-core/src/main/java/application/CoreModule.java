package application;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import micro.ApplicationContributionModule;

public class CoreModule extends AbstractModule implements ApplicationContributionModule{
    @Override
    protected void configure() {
        bind(ApplicationModel.class).in(Singleton.class);
    }
}
