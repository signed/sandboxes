package application;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public abstract class BaseModuleForToolBarContributor extends AbstractModule{
    public void contributeToToolBarWith(Class<? extends ToolBarContribution> contributor){
        Multibinder<ToolBarContribution> toolBarContributionMultibinder = Multibinder.newSetBinder(binder(), ToolBarContribution.class);
        toolBarContributionMultibinder.addBinding().to(contributor);
    }

}
