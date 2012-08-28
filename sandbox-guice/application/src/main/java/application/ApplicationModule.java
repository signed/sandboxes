package application;

import application.input.InputView;
import application.input.InputViewJavaFx;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ApplicationModel.class).in(Singleton.class);
        bind(InputView.class).to(InputViewJavaFx.class).in(Singleton.class);
    }
}
