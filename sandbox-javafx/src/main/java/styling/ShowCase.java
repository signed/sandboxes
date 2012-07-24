package styling;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

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
    private Pane centerPane = new Pane();

    public ShowCase(int width, int height) {
        centerPane.setMaxHeight(height);
        centerPane.setMaxWidth(width);
        componentHolderPane.setTop(top);
        BorderPane.setAlignment(top, Pos.TOP_CENTER);
        componentHolderPane.setRight(right);
        BorderPane.setAlignment(right, Pos.CENTER_RIGHT);
        componentHolderPane.setBottom(bottom);
        BorderPane.setAlignment(bottom, Pos.BOTTOM_CENTER);
        componentHolderPane.setLeft(left);
        BorderPane.setAlignment(left, Pos.CENTER_LEFT);
        componentHolderPane.setCenter(centerPane);
        BorderPane.setAlignment(centerPane, Pos.CENTER);
    }

    public void display(Exhibit exhibit) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(exhibit.getComponent());
    }

    public void integrateInto(Pane parent) {
        parent.getChildren().add(componentHolderPane);
    }
}
