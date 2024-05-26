package bugs.contextmenu.RT25529;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ContextMenuStaysInForegroundWhenDebugging extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static class DoSomething implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            //Put a breakpoint in line 31
            System.out.println("Hello Debugger");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        ContextMenu contextMenu = createContextMenu(new DoSomething());
        TableView<String> table = createTable(contextMenu);
        FlowPane pane = new FlowPane();
        pane.getChildren().add(table);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private TableView<String> createTable(ContextMenu contextMenu) {
        TableView<String> table = new TableView<>();
        table.setContextMenu(contextMenu);
        table.getColumns().add(new TableColumn<String, String>());
        return table;
    }

    private ContextMenu createContextMenu(EventHandler<ActionEvent> contextMenuAction) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item = new MenuItem("Debug this action to see the issue");
        item.setOnAction(contextMenuAction);
        contextMenu.getItems().add(item);
        return contextMenu;
    }
}
