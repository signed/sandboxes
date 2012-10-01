package bugs.combobox.RT24412;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class ComboBoxDoesNotSelectAfterClearing extends Application {

    private String elementToSelect = "one";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(500);
        pane.getChildren().add(comboBox);
        comboBox.setItems(FXCollections.observableArrayList("zero", elementToSelect, "two"));
        stage.setScene(new Scene(pane));
        stage.show();

        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object o1) {
                System.out.println(o1);
            }
        });

        comboBox.getSelectionModel().select(elementToSelect);
        comboBox.getSelectionModel().clearSelection();
        comboBox.getSelectionModel().select(elementToSelect);
    }
}