package bugs.table.RT25338;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;


public class TableViewLeaksMemory extends Application {
    TableView tableView = new TableView();
    SimpleFactory factory = new SimpleFactory();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        final FlowPane pane = new FlowPane(Orientation.VERTICAL);
        createItems(tableView);
        Button addButton = createButton();
        Button dropTable = new Button("Drop Table");
        dropTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent o) {
                pane.getChildren().remove(tableView);
                tableView = null;
            }
        });
        pane.getChildren().add(tableView);
        pane.getChildren().add(addButton);
        pane.getChildren().add(dropTable);
        stage.setScene(new Scene(pane));
        stage.setHeight(500);
        stage.show();
    }

    private void createItems(TableView tableView) {
        List items = new ArrayList();
        for (int i = 0; i < 6; i++) {
            items.add(new Object());
        }
        tableView.setItems(new ObservableListWrapper(items));
    }

    private Button createButton() {
        Button addButton = new Button("Replace Columns");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableView.getColumns().setAll(createColumns());
            }
        });
        return addButton;
    }

    private List<TableColumn> createColumns() {
        ArrayList<TableColumn> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            TableColumn<Object, String> column = new TableColumn<>();
            column.setCellValueFactory(factory);
            list.add(column);
        }
        return list;
    }

    private static class SimpleFactory implements Callback<TableColumn.CellDataFeatures<Object, String>, ObservableValue<String>> {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Object, String> namedProfileCellDataFeatures) {
            return new SimpleStringProperty("X");
        }
    }
}