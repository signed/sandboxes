package combobox.RT23226;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import static javafx.collections.FXCollections.observableArrayList;


public class ButtonAreaOutOfSyncWithSelectionModel extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();
        final ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(500);
        comboBox.setItems(observableArrayList("one", "two"));
        comboBox.setPromptText("juhu");
        pane.getChildren().add(comboBox);

        final Label selectedItemLabel = new Label("false");
        pane.getChildren().add(selectedItemLabel);

        stage.setScene(new Scene(pane));
        stage.show();

        comboBox.getSelectionModel().select("two");
        comboBox.setItems(observableArrayList("three", "four"));


        selectedItemLabel.setText("selected item: "+ comboBox.getSelectionModel().selectedItemProperty().get());

    }
}