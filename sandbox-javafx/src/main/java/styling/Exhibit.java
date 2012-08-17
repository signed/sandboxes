package styling;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Exhibit implements Manican, Scalable{

    private final String name;
    private Node node;

    public Exhibit(String name, Node node) {
        this.name = name;
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


    public void putStyleClassesInto(StyleClassSink sink) {
        Set<String> strings = extractedStyleClassesFromNode(node);
        sink.consume(new ArrayList<>(strings));
    }

    private Set<String> extractedStyleClassesFromNode(Node node) {
        Set<String> foundStyleSheets = new HashSet<>();
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            foundStyleSheets.addAll(parent.getStyleClass());
            for(Node child:parent.getChildrenUnmodifiable()) {
                Set<String> childClasses = extractedStyleClassesFromNode(child);
                foundStyleSheets.addAll(childClasses);
            }
        }
        return foundStyleSheets;
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

    public String getName() {
        return name;
    }
}
