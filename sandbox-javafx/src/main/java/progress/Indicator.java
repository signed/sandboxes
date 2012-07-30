package progress;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Indicator extends Application{

    private final ProgressIndicator progressIndicator = new ProgressIndicator();
    private final StackPane stackPane = new StackPane();

    public static void main(String[]args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        VBox vBox = new VBox();
        HBox buttonBox = new HBox();
        Button hide = new Button("Hide");
        hide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                hideProgressIndicator();
            }
        });
        Button show = new Button("show");
        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showProgressIndicator();
            }
        });
        buttonBox.getChildren().addAll(show, hide);
        hideProgressIndicator();
        stackPane.getChildren().addAll(new Button("behind the progress indicator"),progressIndicator);
        vBox.getChildren().addAll(buttonBox, stackPane);
        stage.setScene(new Scene(vBox));
        stage.show();
    }

    private void showProgressIndicator() {
        progressIndicator.setVisible(true);
    }

    private void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }
}
