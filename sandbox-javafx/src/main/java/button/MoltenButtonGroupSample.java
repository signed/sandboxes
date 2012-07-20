package button;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MoltenButtonGroupSample extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));

        MoltenToggleButtonGroup moltenToggleButtonGroup = new MoltenToggleButtonGroup();
        moltenToggleButtonGroup.addToggleButton("Left Button");
        moltenToggleButtonGroup.addToggleButton("Center Button");
        ToggleButton button = moltenToggleButtonGroup.addToggleButton("another");
        moltenToggleButtonGroup.addToggleButton("one");
        moltenToggleButtonGroup.addToggleButton("Right Button");

        HBox hBox = new HBox();
        moltenToggleButtonGroup.addButtonsTo(hBox);
        root.getChildren().addAll(hBox);
        primaryStage.show();
    }
}

