package contributionTwo;

import application.ToolBarContribution;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class ContributionTwoModule extends AbstractModule{
    @Override
    protected void configure() {
        Multibinder<ToolBarContribution> toolBarContributionMultibinder = Multibinder.newSetBinder(binder(), ToolBarContribution.class);
        toolBarContributionMultibinder.addBinding().to(ContributionTwo.class);

        bind(TwoView.class).to(TwoViewJavaFx.class);
    }
}
