package combobox;

import domain.ANumber;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ComboBoxExample extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();
        ComboBox<ANumber> comboBox = new ComboBox<>();
        Callback<ListView<ANumber>, ListCell<ANumber>> cellFactory = new Callback<ListView<ANumber>, ListCell<ANumber>>() {
            @Override
            public ListCell<ANumber> call(ListView<ANumber> aNumberListView) {
                return new ListCell<ANumber>(){
                    @Override
                    protected void updateItem(ANumber aNumber, boolean b) {
                        super.updateItem(aNumber, b);
                        if(null == aNumber) return;
                        setText(aNumber.doubleProperty().getValue().toString());
                    }
                };
            }
        };
        comboBox.setButtonCell(cellFactory.call(null));
        comboBox.setCellFactory(cellFactory);

        comboBox.setItems(FXCollections.observableArrayList(new ANumber(2.1), new ANumber(2.2)));

        pane.setCenter(comboBox);
        stage.setScene(new Scene(pane));
        stage.show();
    }
}
