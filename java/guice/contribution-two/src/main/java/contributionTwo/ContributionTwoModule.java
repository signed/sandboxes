package contributionTwo;

import application.extensionpoints.ToolBarExtensionPoint;
import com.github.signed.microplugin.core.ApplicationContributionModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ContributionTwoModule extends AbstractModule implements ApplicationContributionModule {
    @Override
    protected void configure() {
        ToolBarExtensionPoint.contributeTo(binder()).theImplementer(ContributionTwo.class);
        bind(TwoView.class).to(TwoViewJavaFx.class).in(Singleton.class);
    }
}
