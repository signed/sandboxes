package contributionOne;

import lang.Closure;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class OneViewJavaFx implements OneView {
    private final Button one = new Button("eins");

    @Override
    public void onAction(final Closure closure) {
    one.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            closure.execute();
        }
    });
    }

    @Override
    public void addTo(ToolBar toolBar) {
        toolBar.getItems().add(one);
    }

}
