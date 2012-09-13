package contributionOne;

import application.BaseModuleForToolBarContributor;
import contributions.ApplicationContributionModule;

public class ContributionOneModule extends BaseModuleForToolBarContributor implements ApplicationContributionModule {
    @Override
    protected void configure() {
        contributeToToolBarWith(ContributionOne.class);
        bind(OneView.class).to(OneViewJavaFx.class);
    }
}
