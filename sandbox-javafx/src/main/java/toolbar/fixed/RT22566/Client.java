package toolbar.fixed.RT22566;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private BorderPane pane = new BorderPane();

    @Override
    public void start(Stage stage) {
        stage.setWidth(800);
        stage.setHeight(600);
        Scene scene = createScene();
        stage.setScene(scene);
        stage.show();
    }

    public Scene createScene() {
        createToolbar();
        createSlider();
        createTable();
        return new Scene(pane);
    }

    private void createToolbar() {
        ToolBar toolbar = new ToolBar();
        Button button = new Button("Move the slider around to freeze me.");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("I am still active - I just don't look the way.");
            }
        });
        button.setTooltip(new Tooltip("My tooltip is still fine!"));
        toolbar.getItems().add(button);
        pane.setTop(toolbar);
    }

    public void createSlider() {
        Slider slider = new Slider();
        slider.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o1) {
                createTable();
            }
        });
        pane.setCenter(slider);
    }

    public void createTable() {
        TableView<Object> tableView = new TableView<>();
        List<TableColumn<Object, ?>> columns = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            TableColumn<Object, Number> column = new TableColumn<>();
            columns.add(column);
        }
        tableView.getColumns().setAll(columns);
        pane.setBottom(tableView);
    }
}