package progress.variants;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public interface ProgressBarAndButtonStrategy {
    Pane createPane();

    void configureButton(Button button);

    void configureProgressBarr(ProgressBar progressIndicator, Button button);
}
