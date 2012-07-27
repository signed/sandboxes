package styling;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ShowCase {
    private static Label createLabel(String name){
        Label label = new Label(name);
        label.setStyle("-fx-border-color:red;");
        return label;
    }

    private final BorderPane componentHolderPane = new BorderPane();
    private final Label top = createLabel("up");
    private final Label right = createLabel("right");
    private final Label bottom = createLabel("bottom");
    private final Label left = createLabel("left");

    public ShowCase(int width) {
        componentHolderPane.setMinWidth(width);

        componentHolderPane.setTop(top);
        BorderPane.setAlignment(top, Pos.TOP_CENTER);
        componentHolderPane.setRight(right);
        BorderPane.setAlignment(right, Pos.CENTER_RIGHT);
        componentHolderPane.setBottom(bottom);
        BorderPane.setAlignment(bottom, Pos.BOTTOM_CENTER);
        componentHolderPane.setLeft(left);
        BorderPane.setAlignment(left, Pos.CENTER_LEFT);
    }

    public void display(Exhibit exhibit) {
        Node newComponent = exhibit.getComponent();
        Group group = new Group();
        group.getChildren().add(newComponent);
        componentHolderPane.setCenter(group);
    }

    public void integrateInto(NodeContainer parent) {
        parent.add(componentHolderPane);
    }
}
