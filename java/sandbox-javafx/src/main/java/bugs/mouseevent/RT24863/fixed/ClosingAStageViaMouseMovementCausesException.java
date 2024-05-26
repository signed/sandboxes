package bugs.mouseevent.RT24863.fixed;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClosingAStageViaMouseMovementCausesException extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        stage.addEventHandler(MouseEvent.ANY, new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });
        Label label = new Label("Windows: Move the mouse here to close the program and cause an exception.");
        stage.setScene(new Scene(label));
        stage.show();
    }
}