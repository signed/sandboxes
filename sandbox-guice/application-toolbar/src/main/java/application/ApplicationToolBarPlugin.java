package application;

import application.extensionpoints.ToolBarContribution;
import com.google.inject.Inject;
import extensionpoints.ViewContribution;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import com.github.signed.microplugin.core.AllContributors;

public class ApplicationToolBarPlugin implements ViewContribution {

    private final AllContributors<ToolBarContribution> allToolBarContributors;

    @Inject
    public ApplicationToolBarPlugin(AllContributors<ToolBarContribution> allToolBarContributors){
        this.allToolBarContributors = allToolBarContributors;
    }

    @Override
    public void addTo(Pane pane) {
        ToolBar toolBar = createToolBar();
        pane.getChildren().add(toolBar);
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();
        for (ToolBarContribution contribution : allToolBarContributors) {
            contribution.addTo(toolBar);
        }
        return toolBar;
    }
}