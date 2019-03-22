package application.input;

import lang.ArgumentClosure;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class InputViewJavaFx implements InputView {
    private final TextArea textArea = new TextArea();
    @Override
    public void onChange(final ArgumentClosure<String> closure) {
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                closure.execute(s1);
            }
        });
    }

    @Override
    public void display(String message) {
        textArea.textProperty().set(message);
    }

    @Override
    public void addTo(Pane pane) {
        pane.getChildren().add(textArea);
    }
}
