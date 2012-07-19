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
        TableView<Integer> tableView = new TableView<>();
        tableView.getColumns().add(column);

        List<Integer> values = new ArrayList<>();
        values.add(27);
        values.add(-43);

        tableView.setItems(new ObservableListWrapper<>(values));

        pane.getChildren().add(tableView);
        stage.setScene(new Scene(pane));
        stage.setHeight(500);
        stage.show();
    }


    public static class RendererTableCellFactory<T> implements Callback<TableColumn<T, Integer>, TableCell<T, Integer>> {

        @Override
        public TableCell<T, Integer> call(TableColumn<T, Integer> tableViewTableColumn) {
            return new NegativeIntegerReadRenderCell<T>();
        }
    }

    public static class NegativeIntegerReadRenderCell<T> extends TableCell<T, Integer> {


        @Override
        protected void updateItem(Integer integer, boolean isEmpty) {
            super.updateItem(integer, isEmpty);
            if (null == integer) {
                return;
            }
            setText(String.valueOf(Math.round(integer.doubleValue())));
            if ((integer.intValue() < 0)) {
                setTextFill(Color.RED);
            }
        }
    }
}