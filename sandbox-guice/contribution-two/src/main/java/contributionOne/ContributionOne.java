package contributionOne;

import application.ApplicationModel;
import application.ToolBarContribution;
import javafx.scene.control.ToolBar;

import javax.inject.Inject;

public class ContributionOne implements ToolBarContribution{
    private final OneView view;

    @Inject
    public ContributionOne(OneView view, ApplicationModel model){
        this.view = view;
        new OnePresenter(view, model);
    }

    @Override
    public void addTo(ToolBar toolBar) {
        view.addTo(toolBar);
    }
}
