package bugs.table.RT22385;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TableViewLeaksMemory extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        TableView tableView = new TableView();
        tableView.setItems(new ObservableListWrapper(Collections.singletonList(new Object())));
        Button addButton = createAddButton(tableView);
        Button removeButton = createRemoveButton(tableView);
        pane.getChildren().add(tableView);
        pane.getChildren().add(addButton);
        pane.getChildren().add(removeButton);
        stage.setScene(new Scene(pane));
        stage.setHeight(500);
        stage.show();
    }

    private Button createRemoveButton(final TableView tableView) {
        Button removeButton = new Button("Remove Columns");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int count = tableView.getColumns().size();
                if (count >= 5) {
                    tableView.getColumns().remove(count - 5, count);
                }
            }
        });
        return removeButton;
    }

    private Button createAddButton(final TableView tableView) {
        Button addButton = new Button("Add Columns");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableView.getColumns().addAll(createFiveColumns());
            }
        });
        return addButton;
    }

    private List<TableColumn> createFiveColumns() {
        ArrayList<TableColumn> list = new ArrayList<>();
        list.add(new TableColumn("1"));
        list.add(new TableColumn("2"));
        list.add(new TableColumn("3"));
        list.add(new TableColumn("4"));
        list.add(new TableColumn("5"));
        return list;
    }
}