package table.notreported;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class TableColumnDoesNotGrowToFitCellValue extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();
        TableView<String> view = createATableWithASingleColumn(pane);
        ObservableList<String> someStrings = FXCollections.observableArrayList(
                "This is the content that should trigger a column width grow"
        );
        view.setItems(someStrings);

        stage.setScene(new Scene(pane));
        stage.show();
    }

    private TableView<String> createATableWithASingleColumn(FlowPane pane) {
        TableView<String> view = new TableView<>();
        pane.getChildren().add(view);
        TableColumn<String, String> column = new TableColumn<>("Description");

        Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>> callback = new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> stringStringCellDataFeatures) {
                return new SimpleStringProperty(stringStringCellDataFeatures.getValue());
            }
        };
        column.setCellValueFactory(callback);
        view.getColumns().add(column);
        return view;
    }
}