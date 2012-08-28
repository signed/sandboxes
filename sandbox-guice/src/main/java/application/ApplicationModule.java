package application;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import contribution.ContributionOne;
import contribution.JavaFxOneView;
import contribution.OneView;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OneView.class).to(JavaFxOneView.class);
        bind(ApplicationModel.class).in(Singleton.class);

        Multibinder<ToolBarContribution> toolBarContributionMultibinder = Multibinder.newSetBinder(binder(), ToolBarContribution.class);
        toolBarContributionMultibinder.addBinding().to(ContributionOne.class);
    }
}
