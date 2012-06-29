package toolbar.RT22566;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ClientWithButtons extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private BorderPane pane = new BorderPane();

    @Override
    public void start(Stage stage) {
        stage.setWidth(800);
        stage.setHeight(600);
        Scene scene = createScene();
        stage.setScene(scene);
        stage.show();
    }

    public Scene createScene() {
        createToolbar();
        createSlider();
        createTable();
        return new Scene(pane);
    }

    private void createToolbar() {
        ToolBar toolbar = new ToolBar();
        Button button = new Button("Move the slider around to freeze me.");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("I am still active - I just don't look the way.");
            }
        });
        button.setTooltip(new Tooltip("My tooltip is still fine!"));
        toolbar.getItems().add(button);
        pane.setTop(toolbar);
    }

    public void createSlider() {
        Slider slider = new Slider();
        slider.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o1) {
                createTable();
            }
        });
        pane.setCenter(slider);
    }

    public void createTable() {
        HBox hBox = new HBox();
        for (int i = 0; i < 50; i++) {
            hBox.getChildren().add(new Button());
        }
        pane.setBottom(hBox);
    }
}