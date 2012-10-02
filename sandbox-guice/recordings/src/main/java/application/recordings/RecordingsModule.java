package application.recordings;

import extensionpoints.ViewContribution;
import com.github.signed.microplugin.core.ModuleWithContributionSupport;

public class RecordingsModule extends ModuleWithContributionSupport{
    @Override
    protected void configure() {
        contributeTo(ViewContribution.class, RecordingsPlugin.class);
    }
}
