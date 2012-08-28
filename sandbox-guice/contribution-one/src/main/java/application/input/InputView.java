package application.input;

import lang.ArgumentClosure;
import javafx.scene.layout.Pane;

public interface InputView {

    void onChange(ArgumentClosure<String> closure);

    void display(String message);

    void addTo(Pane pane);
}
