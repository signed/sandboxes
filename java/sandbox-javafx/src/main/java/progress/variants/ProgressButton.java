package progress.variants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public interface ProgressButton {
    void showProgress();

    void hideProgress();

    Node getNode();

    void setOnAction(EventHandler<ActionEvent> actionEventEventHandler);
}
