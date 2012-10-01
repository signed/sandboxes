package bugs.combobox;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class ClickOnButtonCellOfComboBoxWhileSceneHasNoFocusCausesButtonCellAreaToFlicker extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();

        VBox left = new VBox();
        left.getChildren().addAll(createFilterBox("threads"), createFilterBox("LogLevel"));

        pane.setLeft(left);
        TableView<String> table = new TableView<>();
        table.getColumns().add(createColumn("one"));
        table.getColumns().add(createColumn("two"));

        table.setItems(new ObservableListWrapper<String>(createItems()));
        pane.setCenter(table);


        stage.setScene(new Scene(pane));
        stage.show();
    }

    private VBox createFilterBox(String promptText) {
        VBox filterBox = new VBox();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(promptText);
        filterBox.setMaxWidth(250);
        filterBox.getChildren().add(comboBox);
        filterBox.getChildren().add(new FlowPane());
        comboBox.setItems(new ObservableListWrapper<>(createItems()));
        return filterBox;
    }

    private TableColumn<String, String> createColumn(String name) {
        TableColumn<String, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> stringStringCellDataFeatures) {
                return new SimpleObjectProperty<>(stringStringCellDataFeatures.getValue());
            }
        });

        column.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
            @Override
            public TableCell<String, String> call(TableColumn<String, String> stringStringTableColumn) {
                return new TableCell<String, String>(){

                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);
                        if(null == s){
                            setText("null");
                            return;
                        }
                        setText(s);
                    }
                };
            }
        });
        return column;
    }

    private List<String> createItems() {
        List<String> items = new ArrayList<>();
        for(int index=0; index<1000; ++index){
            items.add("item " + String.format("%03d with some really long text in it at least that was the case in the real application", index));
        }
        return items;
    }
}
