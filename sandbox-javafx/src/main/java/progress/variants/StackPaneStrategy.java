package progress.variants;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class StackPaneStrategy implements ProgressBarAndButtonStrategy {

    @Override
    public Pane createPane() {
        return new StackPane();
    }

    @Override
    public void configureButton(Button button) {
        button.setMaxWidth(Double.MAX_VALUE);
    }

    @Override
    public void configureProgressBarr(ProgressBar progressIndicator, Button button) {
        progressIndicator.setMaxWidth(27);
        progressIndicator.maxWidthProperty().bind(button.widthProperty());
        progressIndicator.prefWidthProperty().bind(button.widthProperty());
        progressIndicator.minWidthProperty().bind(button.widthProperty());
    }
}
