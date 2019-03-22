package application.extensionpoints;

import javafx.scene.control.ToolBar;

public interface ToolBarContribution {
    void addTo(ToolBar toolBar);
}
