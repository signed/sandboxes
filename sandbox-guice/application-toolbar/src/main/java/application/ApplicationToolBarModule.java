package application;

import com.github.signed.microplugin.core.ModuleWithContributionSupport;
import extensionpoints.ViewExtensionPoint;

public class ApplicationToolBarModule extends ModuleWithContributionSupport{
    @Override
    protected void configure() {
        ViewExtensionPoint.contributeTo(binder()).theImplementer(ApplicationToolBarPlugin.class);
    }
}
