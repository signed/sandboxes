package contributionOne;

import lang.Closure;
import javafx.scene.control.ToolBar;

public interface OneView {

    void onAction(Closure closure);

    void addTo(ToolBar toolBar);

}
