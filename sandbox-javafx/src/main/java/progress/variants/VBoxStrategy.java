package progress.variants;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VBoxStrategy implements ProgressButtonStrategy {
    @Override
    public Pane createPane() {
        VBox container = new VBox();
        container.setMaxHeight(VBox.USE_PREF_SIZE);
        return container;
    }

    @Override
    public void configureButton(Button button) {
        button.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(button, Priority.ALWAYS);
    }

    @Override
    public void configureProgressBarr(ProgressIndicator progressIndicator, Button button) {
        progressIndicator.maxWidthProperty().bind(button.widthProperty());
        progressIndicator.prefWidthProperty().bind(button.widthProperty());
        progressIndicator.minWidthProperty().bind(button.widthProperty());
        progressIndicator.setMaxHeight(Control.USE_PREF_SIZE);
    }
}
