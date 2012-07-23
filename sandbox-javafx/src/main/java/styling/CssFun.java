package styling;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CssFun extends Application{

    public static void main(String[] args){
        launch(args);
    }

    final Node styleable = new ToggleButton("Ich bin der Text");

    @Override
    public void start(Stage stage) throws Exception {
        TextArea styleInput = new TextArea();

        styleInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                styleable.setStyle(s1);
            }
        });

        BorderPane componentPane = new BorderPane();
        componentPane.setCenter(styleable);

        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(styleInput, componentPane);
        


        stage.setScene(new Scene(mainLayout));
        stage.show();
    }
}
