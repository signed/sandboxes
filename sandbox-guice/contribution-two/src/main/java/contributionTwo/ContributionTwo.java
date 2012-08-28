package contributionTwo;

import application.ApplicationModel;
import application.ToolBarContribution;
import javafx.scene.control.ToolBar;

import javax.inject.Inject;

public class ContributionTwo implements ToolBarContribution {
    private final TwoView view;

    @Inject
    public ContributionTwo(TwoView view, ApplicationModel model){
        this.view = view;
        new TwoPresenter(view, model);
    }

    @Override
    public void addTo(ToolBar toolBar) {
        view.addTo(toolBar);
    }
}
