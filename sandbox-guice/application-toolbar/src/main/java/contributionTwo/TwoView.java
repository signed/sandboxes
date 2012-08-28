package contributionTwo;

import lang.Closure;
import javafx.scene.control.ToolBar;

public interface TwoView {

    void onAction(Closure closure);

    void addTo(ToolBar toolBar);

}
