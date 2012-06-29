package combobox.RT21953;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NoCellFactory extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ComboBox box = new ComboBox();
        ComplexItemCellFactory cellFactory = new ComplexItemCellFactory();
        box.setCellFactory(cellFactory);
        box.getItems().add(new ComplexItem());
        stage.setScene(new Scene(box));
        box.getSelectionModel().select(null);
        stage.show();
    }

    public class ComplexItemCellFactory implements Callback<ListView<ComplexItem>, ListCell<ComplexItem>> {

        @Override
        public ListCell<ComplexItem> call(ListView<ComplexItem> tListView) {
            return new ComplexItemListCell();
        }
    }

    public class ComplexItemListCell extends ListCell<ComplexItem> {

        @Override
        protected void updateItem(ComplexItem item, boolean b) {
            super.updateItem(item, b);
            if (item == null){
                setText("");
                return;
            }
            setText(item.getName());
        }
    }

    public class ComplexItem {

        public String getName() {
            return "This should be shown.";
        }

        @Override
        public String toString() {
            return "toString was called.";
        }
    }
}