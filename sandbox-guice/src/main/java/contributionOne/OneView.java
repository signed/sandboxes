package contributionOne;

import application.ModularJFx;
import javafx.scene.control.ToolBar;

public interface OneView {

    void onAction(ModularJFx.Closure closure);

    void addTo(ToolBar toolBar);

}
