package progress;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ProgressBarAndButtonInVBox implements ProgressButton {
    private final VBox container = new VBox();
    private final Button button = new Button("long running job");
    private final ProgressBar progressIndicator = new ProgressBar();

    public ProgressBarAndButtonInVBox() {
        button.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(button, Priority.ALWAYS);

        progressIndicator.setMaxWidth(27);
        progressIndicator.maxWidthProperty().bind(button.widthProperty());
        progressIndicator.minWidthProperty().bind(button.widthProperty());
        progressIndicator.prefHeightProperty().bind(button.widthProperty());

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
