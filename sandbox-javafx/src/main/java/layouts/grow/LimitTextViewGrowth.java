package layouts.grow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LimitTextViewGrowth extends Application {


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox container = new HBox();
        TextArea textArea = new TextArea("some bogus text");
        container.getChildren().add(textArea);
        textArea.setMaxHeight(Control.USE_PREF_SIZE);
        stage.setScene(new Scene(container));
        stage.show();
    }
}
