package styling;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import static styling.Family.adapted;

public class StyleInputs {
    private final TabPane tabPane = new TabPane();

    public StyleInputs() {

    }

    public void integrate(StylePad pad, String padName){
        ObservableList<Tab> tabs = tabPane.getTabs();
        Tab styleTab = new Tab(padName);
        pad.integrateInto(adapted(styleTab));
        tabs.add(styleTab);

    }

    public void integrateInto(NodeContainer parent) {
        parent.add(tabPane);
    }
}
