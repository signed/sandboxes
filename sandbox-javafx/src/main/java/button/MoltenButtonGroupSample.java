package button;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MoltenButtonGroupSample extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));

        MoltenToggleButtonBar moltenToggleButtonBar = new MoltenToggleButtonBar();
        moltenToggleButtonBar.addToggleButton("Left Button");
        moltenToggleButtonBar.addToggleButton("Center Button");
        moltenToggleButtonBar.addToggleButton("another");
        moltenToggleButtonBar.addToggleButton("one");
        moltenToggleButtonBar.addToggleButton("Right Button");
        moltenToggleButtonBar.each(new Callback<ToggleButton, Void>() {
            @Override
            public Void call(final ToggleButton toggleButton) {
                toggleButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println(toggleButton.getText());
                    }
                });
                return null;
            }
        });

        HBox hBox = new HBox();
        moltenToggleButtonBar.addButtonsTo(hBox);

        hBox.getChildren().add(new ToggleButton("default size"));

        root.getChildren().addAll(hBox);
        primaryStage.show();
    }
}

