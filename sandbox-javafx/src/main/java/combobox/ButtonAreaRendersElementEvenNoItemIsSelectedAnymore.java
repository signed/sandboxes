package combobox;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import static javafx.collections.FXCollections.observableArrayList;


public class ButtonAreaRendersElementEvenNoItemIsSelectedAnymore extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();
        final ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(500);
        comboBox.buttonCellProperty().set(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean isEmpty) {
                super.updateItem(item, isEmpty);
                if (isEmpty) {
                    return;
                }
                setText(item);
            }
        });

        comboBox.setItems(observableArrayList("one", "two"));

        final Label selectedItemLabel = new Label();

        Button button = new Button("Exchange data");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                exchangeData(comboBox);
            }
        });


        Label reproductionDescription = new Label("Steps:\n1. select an item in the combobox\n2. Press the Exchange data button\n-> There is still something rendered in the button area of the combo box although the selection model states that no item is selected.");


        pane.getChildren().add(comboBox);
        pane.getChildren().add(button);
        pane.getChildren().add(selectedItemLabel);
        pane.getChildren().add(reproductionDescription);
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if(null == s1){
                    selectedItemLabel.setText("no item selected");
                }else{
                    selectedItemLabel.setText(s1);
                }
            }
        });

        stage.setScene(new Scene(pane));
        stage.show();
    }

    private void exchangeData(ComboBox<String> comboBox) {
        comboBox.setItems(observableArrayList("three", "four"));
    }
}