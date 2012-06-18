package table;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class TableSample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Group root = new Group();
        primaryStage.setScene(new Scene(root));


        TableView<Person> tableView = createTable();
        borderPane.setCenter(tableView);

        root.getChildren().add(borderPane);

        primaryStage.show();

        final ObservableList<Person> data = FXCollections.observableArrayList(
                new Person("Jacob", "Smith", "jacob.smith@example.com"),
                new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                new Person("Ethan", "Williams", "ethan.williams@example.com"),
                new Person("Emma", "Jones", "emma.jones@example.com"),
                new Person("Michael", "Brown", "michael.brown@example.com")
        );
        tableView.setItems(data);

    }

    private TableView<Person> createTable() {
        TableColumn<Person, String> firstNameCol = new TableColumn<>();
        firstNameCol.setText("First");
        PropertyValueFactory firstName = new PropertyValueFactory("firstName");

        firstNameCol.setCellValueFactory(firstName);

        TableColumn<Person, String> lastNameCol = new TableColumn<>();
        lastNameCol.setText("Last");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));

        TableColumn<Person, String> emailCol = new TableColumn<>();
        emailCol.setText("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));


        TableView<Person> tableView = new TableView<>();
        ObservableList<TableColumn<Person, ?>> columns = tableView.getColumns();
        columns.add(firstNameCol);
        columns.add(lastNameCol);
        columns.add(emailCol);
        return tableView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
