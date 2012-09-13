package application;

import application.input.InputView;
import application.input.InputViewJavaFx;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import micro.ApplicationContributionModule;

public class ApplicationModule  extends AbstractModule implements ApplicationContributionModule {

    @Override
    protected void configure() {
        bind(InputView.class).to(InputViewJavaFx.class).in(Singleton.class);
    }
}
