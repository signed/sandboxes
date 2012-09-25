package bugs.table.RT25211;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ColumnsDoNotPropagateStyleClasses extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TableView table = new TableView();
        TableColumn column = new TableColumn();
        column.getStyleClass().add("newstyle");
        table.getColumns().add(column);
        stage.setScene(new Scene(table));
        stage.show();
    }
}