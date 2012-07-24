package styling;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

public class Family {
    public static SomethingWithChildren from(final Pane pane){
        return new SomethingWithChildren() {
            @Override
            public ObservableList<Node> getChildren() {
                return pane.getChildren();
            }
        };
    }

    public static SomethingWithChildren adapted(final SplitPane splitPane){
        return new SomethingWithChildren() {
            @Override
            public ObservableList<Node> getChildren() {
                return splitPane.getItems();
            }
        };
    }

}
