package contributionTwo;

import application.ToolBarContribution;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;
import contributions.ApplicationContributionModule;

public class ContributionTwoModule extends AbstractModule implements ApplicationContributionModule {
    @Override
    protected void configure() {
        Multibinder<ToolBarContribution> toolBarContributionMultibinder = Multibinder.newSetBinder(binder(), ToolBarContribution.class);
        toolBarContributionMultibinder.addBinding().to(ContributionTwo.class);

        bind(TwoView.class).to(TwoViewJavaFx.class);
    }

    @Override
    public Module instance() {
        return this;
    }
}
