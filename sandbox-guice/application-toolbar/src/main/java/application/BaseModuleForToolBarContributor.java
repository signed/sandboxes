package application;

import micro.ModuleWithContributionSupport;

public abstract class BaseModuleForToolBarContributor extends ModuleWithContributionSupport{
    public void contributeToToolBarWith(Class<? extends ToolBarContribution> contributor){
        contributeTo(ToolBarContribution.class, contributor);
    }
}
