package application.input;

import com.github.signed.microplugin.core.ModuleWithContributionSupport;
import com.google.inject.Singleton;
import extensionpoints.ViewExtensionPoint;

public class InputViewModule extends ModuleWithContributionSupport{
    @Override
    protected void configure() {
        ViewExtensionPoint.contribute(binder()).aView(InputViewPlugin.class);
        bind(InputView.class).to(InputViewJavaFx.class).in(Singleton.class);
    }
}
