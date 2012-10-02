package application.recordings;

import com.github.signed.microplugin.core.ModuleWithContributionSupport;
import extensionpoints.ViewExtensionPoint;

public class RecordingsModule extends ModuleWithContributionSupport{
    @Override
    protected void configure() {
        ViewExtensionPoint.contribute(binder()).aView(RecordingsPlugin.class);
    }
}
