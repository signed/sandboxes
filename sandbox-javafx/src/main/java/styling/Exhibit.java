package styling;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

public class Exhibit implements Manican, Scalable{

    private Node node;

    public Exhibit(Node node) {
        this.node = node;
    }

    @Override
    public void incrementScale() {
        scaleTo(currentScale() + 0.25);
    }

    @Override
    public void decrementScale() {
        scaleTo(currentScale() - 0.25);
    }

    @Override
    public void resetScale() {
        scaleTo(1.0);
    }

    private double currentScale(){
        return node.getScaleX();
    }

    private void scaleTo( double scale){
        node.setScaleX(scale);
        node.setScaleY(scale);
    }

    @Override
    public void setStyle(String style) {
        node.setStyle(style);
    }

    @Override
    public void clearStyle() {
        node.setStyle("");
    }

    public Node getComponent() {
        return node;
    }

    public void useInMemoryStyleSheetAt(String url) {
        ObservableList<String> stylesheets = getParent().getStylesheets();
        stylesheets.remove(url);
        stylesheets.add(url);
    }

    private Parent getParent() {
        return (Parent) node;
    }
}
