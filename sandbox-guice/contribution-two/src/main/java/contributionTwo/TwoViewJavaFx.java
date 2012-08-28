package contributionTwo;

import lang.Closure;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class TwoViewJavaFx implements TwoView {
    private final Button one = new Button("zwei");

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
