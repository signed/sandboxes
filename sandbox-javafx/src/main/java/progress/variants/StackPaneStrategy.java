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
        progressIndicator.maxWidthProperty().bind(button.widthProperty());
        progressIndicator.prefWidthProperty().bind(button.widthProperty());
        progressIndicator.minWidthProperty().bind(button.widthProperty());

        progressIndicator.maxHeightProperty().bind(button.heightProperty());
        progressIndicator.prefHeightProperty().bind(button.heightProperty());
        progressIndicator.minHeightProperty().bind(button.heightProperty());
    }
}
