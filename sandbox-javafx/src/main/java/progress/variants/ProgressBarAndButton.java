package progress.variants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ProgressBarAndButton implements ProgressButton {
    private Pane container = new StackPane();
    private final Button button = new Button("long running job");
    private final ProgressBar progressIndicator = new ProgressBar();
    private final ProgressBarAndButtonStrategy strategy;

    public ProgressBarAndButton(ProgressBarAndButtonStrategy strategy) {
        this.strategy = strategy;
        container = this.strategy.createPane();
        this.strategy.configureButton(button);
        this.strategy.configureProgressBarr(progressIndicator, button);

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
