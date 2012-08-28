package contributionOne;

import application.ToolBarContribution;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import contributions.ApplicationContributionModule;

public class ContributionOneModule extends AbstractModule implements ApplicationContributionModule {
    @Override
    protected void configure() {
        Multibinder<ToolBarContribution> toolBarContributionMultibinder = Multibinder.newSetBinder(binder(), ToolBarContribution.class);
        toolBarContributionMultibinder.addBinding().to(ContributionOne.class);

        bind(OneView.class).to(OneViewJavaFx.class);
    }
}
