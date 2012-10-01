package bugs.combobox.RT25379;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComboBoxSelectionModelFiresWrongChangeEvents extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final ComboBox<String> comboBox = new ComboBox<>();
    private final List<String> choices = new ArrayList<>(Arrays.asList("one", "two", "three"));

    @Override
    public void start(Stage stage) throws Exception {
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ReportChangeEventsOnCommandLine("A"));
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                choices.remove(s1);
                displayRemainingChoices();
            }
        });
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ReportChangeEventsOnCommandLine("B"));

        BorderPane pane = new BorderPane();
        pane.setTop(new Label("select item one or two in the combo box:\nexpected: the selected item is removed from the combo box, two items remain\nactual: the selected item and the next item following in the list are removed"));
        pane.setCenter(comboBox);

        stage.setScene(new Scene(pane));
        stage.show();

        displayRemainingChoices();
    }

    private void displayRemainingChoices() {
        comboBox.setItems(new ObservableListWrapper<>(new ArrayList<>(choices)));
    }

    private static class ReportChangeEventsOnCommandLine implements ChangeListener<String> {
        private final String identifier;

        public ReportChangeEventsOnCommandLine(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
            System.out.println("["+identifier+"] old: " +s+ " new: "+ s1);
        }
    }
}
