package layouts.align;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChildrenInHBox extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox container = new HBox();
        container.setAlignment(Pos.BOTTOM_RIGHT);
        TextArea textArea = new TextArea("some bogus text");
        container.getChildren().addAll(textArea, new Label("With a label"));
        textArea.setMaxHeight(Control.USE_PREF_SIZE);
        stage.setScene(new Scene(container));
        stage.show();
    }
}
