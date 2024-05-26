package experimente;

import javafx.application.Application;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

public class GoneBad extends Application{

    public static void main(String[] args) throws Exception{
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        HBox hBox = new HBox();
        hBox.setPrefWidth(800);
        hBox.setPrefHeight(600);
        hBox.getStyleClass().add("dudu");
        stage.setScene(new Scene(hBox));
        stage.show();

        ObservableMap<Object,Object> properties = hBox.getProperties();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

    }
}
