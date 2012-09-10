package bugs.dialog.RT24855;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.util.Arrays;

public class OpenDialogStageFromMenuItemActionLeavesSystemInUnResponsiveState extends Application{
    public static void main(String [] args){
        launch(args);
    }

    private static class OpenApplicationModalDialog implements EventHandler<ActionEvent> {
        private Stage parent;

        public OpenApplicationModalDialog(Stage parent) {
            this.parent = parent;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            Stage modalStage = new Stage();
            BorderPane borderPane = new BorderPane();
            borderPane.setPrefHeight(200);
            borderPane.setPrefWidth(400);
            borderPane.setCenter(new Label("look at me"));
            modalStage.setScene(new Scene(borderPane));
            modalStage.initOwner(parent);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UTILITY);
            modalStage.showAndWait();
        }
    }

    @Override
    public void start(final Stage stage) throws Exception {
        TableView<String> table = createTableWithContextMenu(new OpenApplicationModalDialog(stage));
        FlowPane pane = new FlowPane();
        pane.getChildren().add(table);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private TableView<String> createTableWithContextMenu(final OpenApplicationModalDialog contextMenuAction) {
        TableView<String> table = new TableView<>();
        table.setItems(new ObservableListWrapper<>(Arrays.asList("one")));
        TableColumn<String, String> column = new TableColumn<>();
        Callback<TableView<String>, TableRow<String>> rowFactory = new Callback<TableView<String>, TableRow<String>>() {
            @Override
            public TableRow<String> call(TableView<String> stringTableView) {
                TableRow<String> tableRow = new TableRow<>();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItems = new MenuItem("put system into unresponsive state");
                menuItems.onActionProperty().set(contextMenuAction);
                contextMenu.getItems().addAll(menuItems);
                tableRow.setContextMenu(contextMenu);
                return tableRow;
            }
        };
        Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>> cellValueFactory = new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> stringStringCellDataFeatures) {
                return new SimpleObjectProperty<>(stringStringCellDataFeatures.getValue());
            }
        };
        column.setCellValueFactory(cellValueFactory);
        table.setRowFactory(rowFactory);
        table.getColumns().add(column);
        return table;
    }
}
