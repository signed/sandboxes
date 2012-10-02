package application;

import extensionpoints.ViewContribution;
import com.github.signed.microplugin.core.ModuleWithContributionSupport;

public class ApplicationToolBarModule extends ModuleWithContributionSupport{
    @Override
    protected void configure() {
        contributeTo(ViewContribution.class, ApplicationToolBarPlugin.class);
    }
}
