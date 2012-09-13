package application.recordings;

import extensionpoints.ViewContribution;
import micro.ModuleWithContributionSupport;

public class RecordingsModule extends ModuleWithContributionSupport{
    @Override
    protected void configure() {
        contributeTo(ViewContribution.class, RecordingsPlugin.class);
    }
}
