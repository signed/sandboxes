package styling;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import java.nio.file.Path;

public class TurnInTranscript implements EventHandler<WindowEvent> {
    private Archivist archivist;
    private StylePad pad;
    private final Path path;

    public TurnInTranscript(StylePad pad, Archivist archivist, Path path) {
        this.archivist = archivist;
        this.pad = pad;
        this.path = path;
    }

    @Override
    public void handle(WindowEvent windowEvent) {
        archivist.transcribeTo(path, pad.getText());
    }
}
