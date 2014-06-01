package bugs.combobox.RT37364;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;

public class EditableComboboxDoesNotUseButtonCell extends Application {
  private static final Entry FIRST = new Entry("first");
  private static final Entry SECOND = new Entry("second");

  public static void main(String... arguments) {
    launch(arguments);
  }

  @Override
  public void start(Stage stage) throws Exception {
    ComboBox<Object> combobox = new ComboBox<>();
    combobox.setEditable(true);
    CellFactory cellFactory = new CellFactory();
    combobox.setCellFactory(cellFactory);
    combobox.setButtonCell(cellFactory.call(null));
    List entries = Arrays.asList(FIRST, SECOND);
    combobox.setItems(new ObservableListWrapper<Object>(entries));
    combobox.getSelectionModel().select(SECOND);
    stage.setScene(new Scene(combobox));
    stage.setWidth(350);
    stage.show();
  }

  private static class CellFactory implements Callback<ListView<Object>, ListCell<Object>> {
    @Override
    public ListCell<Object> call(ListView<Object> objectListView) {
      return new ListCell<Object>() {
        @Override
        public void updateItem(Object item, boolean empty) {
          super.updateItem(item, empty);
          if (item instanceof Entry) {
            setText(((Entry) item).id.toUpperCase());
          } else if (item != null) {
            throw new RuntimeException("Unexpected item");
          }
        }
      };
    }
  }

  private static class Entry {
    public String id;

    public Entry(String id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return id + " - if you can see this, something went wrong.";
    }
  }
}