package application;

import extensionpoints.ViewContribution;
import micro.ModuleWithContributionSupport;

public class ApplicationToolBarModule extends ModuleWithContributionSupport{
    @Override
    protected void configure() {
        contributeTo(ViewContribution.class, ApplicationToolBarPlugin.class);
    }
}
