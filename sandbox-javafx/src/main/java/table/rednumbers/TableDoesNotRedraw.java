package table.rednumbers;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;


public class TableDoesNotRedraw extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TableColumn column = new TableColumn("Value");
        column.setCellFactory(new RendererTableCellFactory());
        Callback callback = new Callback() {
            @Override
            public Object call(Object o) {
                Integer value = (Integer)((TableColumn.CellDataFeatures) o).getValue();
                return new SimpleIntegerProperty(value);
            }
        };
        column.setCellValueFactory(callback);


        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        TableView<Number> tableView = new TableView<>();
        tableView.getColumns().add(column);

        List<Number> values = new ArrayList<>();
        values.add(27);
        values.add(-43);

        tableView.setItems(new ObservableListWrapper<>(values));

        pane.getChildren().add(tableView);
        stage.setScene(new Scene(pane));
        stage.setHeight(500);
        stage.show();
    }


    public static class RendererTableCellFactory<T> implements Callback<TableColumn<T, Number>, TableCell<T, Number>> {

        @Override
        public TableCell<T, Number> call(TableColumn<T, Number> tableViewTableColumn) {
            return new NegativeReadRenderCell<T>();
        }
    }

    public static class NegativeReadRenderCell<T> extends TableCell<T, Number> {


        @Override
        protected void updateItem(Number s, boolean isEmpty) {
            super.updateItem(s, isEmpty);
            if (null == s) {
                return;
            }
            setText(String.valueOf(Math.round(s.doubleValue())));
            if ((s.intValue() < 0)) {
                setTextFill(Color.RED);
            }
        }
    }
}