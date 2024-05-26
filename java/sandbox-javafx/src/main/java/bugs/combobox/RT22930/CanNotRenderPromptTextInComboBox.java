package bugs.combobox.RT22930;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class CanNotRenderPromptTextInComboBox extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(500);
        pane.getChildren().add(comboBox);
        comboBox.buttonCellProperty().set(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean isEmpty) {
                super.updateItem(item, isEmpty);
                if (isEmpty) {
                    setText("Prompt Text");
                    return;
                }
                setText(item);
            }
        });

        comboBox.setItems(FXCollections.observableArrayList("one", "two"));

        stage.setScene(new Scene(pane));
        stage.show();
    }
}