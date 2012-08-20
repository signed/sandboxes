package progress.variants;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;

public interface ProgressButtonStrategy {
    Pane createPane();

    void configureButton(Button button);

    void configureProgressBarr(ProgressIndicator progressIndicator, Button button);
}
