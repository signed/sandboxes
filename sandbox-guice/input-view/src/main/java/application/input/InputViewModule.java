package application.input;

import com.google.inject.Singleton;
import extensionpoints.ViewContribution;
import com.github.signed.microplugin.core.ModuleWithContributionSupport;

public class InputViewModule extends ModuleWithContributionSupport{
    @Override
    protected void configure() {
        contributeTo(ViewContribution.class, InputViewPlugin.class);
        bind(InputView.class).to(InputViewJavaFx.class).in(Singleton.class);
    }
}
