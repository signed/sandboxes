package styling;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface HasChildren {
    ObservableList<Node> getChildren();
}
