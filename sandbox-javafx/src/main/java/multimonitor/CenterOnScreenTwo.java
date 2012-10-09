package multimonitor;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CenterOnScreenTwo implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent windowEvent) {
        Stage stage = (Stage)windowEvent.getSource();
        ObservableList<Screen> screens = Screen.getScreens();
        Screen screen = screens.get(1);
        Rectangle2D bounds = screen.getVisualBounds();

        double x = bounds.getMinX() + (bounds.getMaxX() - bounds.getMinX()) / 2;
        double y = bounds.getMinY() + (bounds.getMaxY() - bounds.getMinY()) / 2;
        stage.setX(x);
        stage.setY(y);
    }
}
