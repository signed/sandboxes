package stage;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EnsureWindowComesOnTop implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent windowEvent) {
        Stage window = (Stage) windowEvent.getSource();
        window.setFullScreen(true);
        window.setFullScreen(false);
    }
}
