package bugs.combobox.fixed.RT21933;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ComboDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ComboBox box = new ComboBox();
        RendererListCellFactory cellFactory = new RendererListCellFactory<>(new ToStringRenderer());
        box.setCellFactory(cellFactory);
        box.getItems().add("The Item");
        stage.setScene(new Scene(box));
        box.getSelectionModel().select(null);
        stage.show();
    }

    public interface ItemRenderer<T> {
        String displayAsString(T t);

        String displayPromptText();
    }

    public class ToStringRenderer implements ItemRenderer {
        @Override
        public String displayAsString(Object o) {
            return o.toString();
        }

        @Override
        public String displayPromptText() {
            return "Select";
        }
    }

    public class RendererListCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {
        private ItemRenderer<T> renderer;

        public RendererListCellFactory(ItemRenderer renderer) {
            this.renderer = renderer;
        }

        @Override
        public ListCell<T> call(ListView<T> tListView) {
            return new RendererListCell<>(renderer);
        }
    }

    public class RendererListCell<T> extends ListCell<T> {
        private ItemRenderer<T> renderer;

        public RendererListCell(ItemRenderer<T> renderer) {
            this.renderer = renderer;
        }

        @Override
        protected void updateItem(T t, boolean b) {
            super.updateItem(t, b);
            if (null == t) {
                setText(renderer.displayPromptText());
                return;
            }
            setText(renderer.displayAsString(t));
        }
    }
}