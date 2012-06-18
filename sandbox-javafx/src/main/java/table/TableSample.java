package table;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class TableSample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Group root = new Group();
        primaryStage.setScene(new Scene(root));


        TableView<ANumber> tableView = createTable();
        borderPane.setCenter(tableView);

        root.getChildren().add(borderPane);

        primaryStage.show();

        final ObservableList<ANumber> data = FXCollections.observableArrayList(
                new ANumber(-9999d),
                new ANumber(0d),
                new ANumber(2222d)
        );
        tableView.setItems(data);

    }

    private TableView<ANumber> createTable() {
        TableColumn<ANumber, Double> firstNameCol = new TableColumn<>();
        firstNameCol.setText("First");

        Callback<TableColumn.CellDataFeatures<ANumber, Double>, ObservableValue<Double>> callback = new PropertyValueFactory<>("double");
        firstNameCol.setCellValueFactory(callback);

        TableView<ANumber> tableView = new TableView<>();
        ObservableList<TableColumn<ANumber, ?>> columns = tableView.getColumns();
        columns.add(firstNameCol);
        return tableView;
    }
}
