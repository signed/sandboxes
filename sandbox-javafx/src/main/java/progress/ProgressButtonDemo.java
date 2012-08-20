package progress;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import progress.variants.ConfiguratbleProgressButton;
import progress.variants.ProgressButton;
import progress.variants.StackPaneStrategy;
import progress.variants.VBoxStrategy;

import java.util.List;

import static java.util.Arrays.asList;

public class ProgressButtonDemo extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    private final LongRunningAction longRunning = new LongRunningAction();
    private final List<ProgressButton> allProgressDisplays = Lists.newArrayList();

    @Override
    public void start(Stage stage) throws Exception {
        VBox topLevelContainer = new VBox();
        HBox buttonBar = createButtonBar();
        HBox controls = createControls();
        topLevelContainer.getChildren().addAll(buttonBar, controls);


        for (final ProgressButton progressDisplay : allProgressDisplays) {
            longRunning.onCompletion(new LongRunningAction.VoidCallback() {
                @Override
                public Void call(Void aVoid) {
                    progressDisplay.hideProgress();
                    return null;
                }
            });
        }

        for (final ProgressButton progressDisplay : allProgressDisplays) {
            longRunning.onStart(new LongRunningAction.VoidCallback() {
                @Override
                public Void call(Void aVoid) {
                    progressDisplay.showProgress();
                    return null;
                }
            });

        }

        for (ProgressButton progressDisplay : allProgressDisplays) {
            progressDisplay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    longRunning.start();
                }
            });

        }

        stage.setScene(new Scene(topLevelContainer));
        stage.show();
    }

    private HBox createControls() {
        HBox controls = new HBox();
        Button complete = new Button("completed");

        complete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                longRunning.completed();
            }
        });
        controls.getChildren().add(complete);
        return controls;
    }

    private HBox createButtonBar() {
        HBox buttonBar = new HBox();
        ProgressButton barVBox = ConfiguratbleProgressButton.aBarWith(new VBoxStrategy());
        ProgressButton barStackPane = ConfiguratbleProgressButton.aBarWith(new StackPaneStrategy());
        ProgressButton wheelStackPane = ConfiguratbleProgressButton.aWheelWith(new StackPaneStrategy());
        allProgressDisplays.addAll(asList(barVBox, barStackPane, wheelStackPane));
        buttonBar.getChildren().addAll(allProgressDisplays());
        return buttonBar;
    }

    private List<Node> allProgressDisplays() {
        return Lists.transform(allProgressDisplays, new Function<ProgressButton, Node>() {
            @Override
            public Node apply(ProgressButton input) {
                return input.getNode();
            }
        });
    }

}
