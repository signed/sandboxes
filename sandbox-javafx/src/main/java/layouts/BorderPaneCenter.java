package layouts;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BorderPaneCenter extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();

        ToggleButton one = new ToggleButton("one and only");
        ToggleButton two = new ToggleButton("two");

        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(one, two);
        buttonContainer.setStyle("-fx-border-color:blue; -fx-border-style:dotted; -fx-border-width:7;");

        Group group = new Group();
        group.getChildren().add(buttonContainer);





        pane.setStyle("-fx-border-color:red");
        pane.setCenter(group);


        Scene scene = SceneBuilder.create().width(800).height(600).root(pane).build();

        stage.setScene(scene);
        stage.show();
    }
}
