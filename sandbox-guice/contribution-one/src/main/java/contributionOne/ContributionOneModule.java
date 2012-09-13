package contributionOne;

import application.BaseModuleForToolBarContributor;

public class ContributionOneModule extends BaseModuleForToolBarContributor {
    @Override
    protected void configure() {
        contributeToToolBarWith(ContributionOne.class);
        bind(OneView.class).to(OneViewJavaFx.class);
    }
}
