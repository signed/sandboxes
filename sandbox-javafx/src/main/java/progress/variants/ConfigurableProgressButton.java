package progress.variants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ConfigurableProgressButton implements ProgressButton {
    private Pane container = new StackPane();
    private final Button button = new Button("long running job");
    private final ProgressIndicator progressIndicator;

    public static ProgressButton aBarWith(ProgressButtonStrategy strategy) {
        ProgressBar bar = new ProgressBar();
        return new ConfigurableProgressButton(bar, strategy);
    }

    public static ProgressButton aWheelWith(ProgressButtonStrategy strategy) {
        return new ConfigurableProgressButton(new ProgressIndicator(), strategy);
    }

    public ConfigurableProgressButton(ProgressIndicator progressIndicator, ProgressButtonStrategy strategy) {
        container = strategy.createPane();
        strategy.configureButton(button);
        this.progressIndicator = progressIndicator;
        strategy.configureProgressBarr(progressIndicator, button);

        container.getChildren().addAll(button, progressIndicator);
    }

    @Override
    public void showProgress() {
        progressIndicator.setVisible(true);
    }

    @Override
    public void hideProgress() {
        progressIndicator.setVisible(false);
    }

    @Override
    public Node getNode() {
        return container;
    }

    @Override
    public final void setOnAction(EventHandler<ActionEvent> actionEventEventHandler) {
        button.setOnAction(actionEventEventHandler);
    }
}
