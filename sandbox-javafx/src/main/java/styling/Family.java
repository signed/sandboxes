package styling;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class Family {


    public static NodeContainer adapted(final Tab tab){
        return new NodeContainer() {
            @Override
            public void add(Node node) {
                tab.setContent(node);
            }
        };
    }


    public static NodeContainer adapted(final Pane pane){
        HasChildren hasChildren = new HasChildren() {
            @Override
            public ObservableList<Node> getChildren() {
                return pane.getChildren();
            }
        };
        return new NodesWithChildren(hasChildren);
    }

    public static NodeContainer adapted(final SplitPane splitPane){
        return new NodesWithChildren(new HasChildren() {
            @Override
            public ObservableList<Node> getChildren() {
                return splitPane.getItems();
            }
        });
    }

}
