package application;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ToolBarModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(ApplicationModel.class).in(Singleton.class);
    }
}
