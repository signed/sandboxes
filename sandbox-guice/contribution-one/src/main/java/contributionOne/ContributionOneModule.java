package contributionOne;

import application.extensionpoints.ToolBarExtensionPoint;
import com.github.signed.microplugin.core.ApplicationContributionModule;
import com.google.inject.AbstractModule;

public class ContributionOneModule extends AbstractModule implements ApplicationContributionModule {
    @Override
    protected void configure() {
        ToolBarExtensionPoint.contribute(binder()).aToolbarItem(ContributionOne.class);
        bind(OneView.class).to(OneViewJavaFx.class);
    }
}
