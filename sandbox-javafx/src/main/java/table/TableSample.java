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
import javafx.stage.Stage;
import javafx.util.Callback;


public class TableSample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        final ObservableList<Person> data = FXCollections.observableArrayList(
                new Person("Jacob", "Smith", "jacob.smith@example.com"),
                new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                new Person("Ethan", "Williams", "ethan.williams@example.com"),
                new Person("Emma", "Jones", "emma.jones@example.com"),
                new Person("Michael", "Brown", "michael.brown@example.com")
        );
        TableColumn<Person, String> firstNameCol = new TableColumn<>();
        firstNameCol.setText("First");
        PropertyValueFactory firstName = new PropertyValueFactory("firstName");

        Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>> callback;
        firstNameCol.setCellValueFactory(callback);

        TableColumn<Person, String> lastNameCol = new TableColumn<>();
        lastNameCol.setText("Last");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));

        TableColumn<Person, String > emailCol = new TableColumn<>();
        emailCol.setText("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));


        TableView<Person> tableView = new TableView<>();
        tableView.setItems(data);
        ObservableList<TableColumn<Person, ?>> columns = tableView.getColumns();
        columns.add(firstNameCol);
        columns.add(lastNameCol);
        columns.add(emailCol);
        //columns.addAll(firstNameCol, lastNameCol, emailCol);
        root.getChildren().add(tableView);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
