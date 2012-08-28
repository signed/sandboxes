package application;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import contribution.JavaFxOneView;
import contribution.OneView;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OneView.class).to(JavaFxOneView.class);
        bind(ApplicationModel.class).in(Singleton.class);
    }
}
