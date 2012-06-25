package table.RT22908;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class TableDemoWithData extends Application {

    public static final int NUMBER_OF_CHILDREN = 24;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();

        TableView<String> view = createSingleColumnTable(pane);
        ObservableList<String> someStrings = FXCollections.observableArrayList(
                "Hello",
                "World"
        );
        view.setItems(someStrings);

        stage.setScene(new Scene(pane));
        stage.show();
    }

    private TableView createSingleColumnTable(FlowPane pane) {
        pane.getChildren().add(new Label("Column width of data rows does not align with header column width. Grab the right end of the column header and resize it to the right to see the problem"));
        TableView view = new TableView();
        pane.getChildren().add(view);
        TableColumn parentColumn = new TableColumn<>();
        view.getColumns().add(parentColumn);
        addChildren(parentColumn);
        return view;
    }

    private void addChildren(TableColumn parentColumn2) {
        for (int i = 0; i < NUMBER_OF_CHILDREN; i++) {
            TableColumn childColumn = new TableColumn<>();
            childColumn.setSortable(false);
            parentColumn2.getColumns().add(childColumn);
        }
    }
}