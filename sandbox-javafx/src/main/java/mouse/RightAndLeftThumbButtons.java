package mouse;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RightAndLeftThumbButtons extends Application{

    public static void main(String [] args){
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Label center = new Label("center");
        center.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(mouseEvent.getEventType());
            }
        });
        borderPane.setCenter(center);
        stage.setScene(new Scene(borderPane));
        stage.show();
    }
}
