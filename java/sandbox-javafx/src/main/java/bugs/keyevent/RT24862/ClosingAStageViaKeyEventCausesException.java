package bugs.keyevent.RT24862;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClosingAStageViaKeyEventCausesException extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        stage.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                stage.close();
            }
        });
        Label label = new Label("Linux: Press any key to close the program and cause an exception.");
        stage.setScene(new Scene(label));
        stage.show();
    }
}