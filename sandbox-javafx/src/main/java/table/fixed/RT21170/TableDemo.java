package table.fixed.RT21170;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class TableDemo extends Application {

    public static final int NUMBER_OF_CHILDREN = 24;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();
        createSingleColumnTable(pane);
        createDualColumnTable(pane);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private void createSingleColumnTable(FlowPane pane) {
        pane.getChildren().add(new Label("Parent column's end does not match child column's end. Drag the parent's right border to the left to see the problem"));
        TableView view = new TableView();
        pane.getChildren().add(view);
        TableColumn parentColumn = new TableColumn<>();
        view.getColumns().add(parentColumn);
        addChildren(parentColumn);
    }

    private void createDualColumnTable(FlowPane pane) {
        pane.getChildren().add(new Label("Parent columns overlap. Drag the visible parent's right border to the left to see the problem"));
        TableView view = new TableView();
        pane.getChildren().add(view);
        TableColumn parentColumn1 = new TableColumn<>();
        view.getColumns().add(parentColumn1);
        addChildren(parentColumn1);
        TableColumn parentColumn2 = new TableColumn<>();
        view.getColumns().add(parentColumn2);
        addChildren(parentColumn2);
    }

    private void addChildren(TableColumn parentColumn2) {
        for (int i = 0; i < NUMBER_OF_CHILDREN; i++) {
            TableColumn childColumn = new TableColumn<>();
            childColumn.setSortable(false);
            parentColumn2.getColumns().add(childColumn);
        }
    }
}