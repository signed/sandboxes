package styling;

import javafx.scene.Node;

public class NodesWithChildren implements  NodeContainer{

    private final HasChildren hasChildren;

    public NodesWithChildren(HasChildren hasChildren) {
        this.hasChildren = hasChildren;

    }

    @Override
    public void add(Node node) {
        hasChildren.getChildren().add(node);
    }
}
