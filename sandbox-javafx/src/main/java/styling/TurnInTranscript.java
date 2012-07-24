package styling;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class TurnInTranscript implements EventHandler<WindowEvent> {
    private Archivist archivist;
    private StylePad pad;

    public TurnInTranscript(Archivist archivist, StylePad pad) {
        this.archivist = archivist;
        this.pad = pad;
    }

    @Override
    public void handle(WindowEvent windowEvent) {
        archivist.transcribe(pad.getText());
    }
}
