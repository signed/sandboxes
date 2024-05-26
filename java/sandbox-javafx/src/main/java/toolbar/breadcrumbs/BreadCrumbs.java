package toolbar.breadcrumbs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This demo illustrate the basic of the styling in JavaFX2.
 *
 * In the demo a bread crumb like component is developed with proper styling.
 *
 * This demo is inspired by the bread crumb bar in the Ensemble demo.
 *
 * http://loop81.blogspot.de/2011/11/javafx-2-breadcrumbs-and-styling.html
 *
 * @author Allitico
 */
public class BreadCrumbs extends Application {

    private ToolBar bc1;
    private ToolBar bc2;
    private ToolBar bc3;

    @Override
    public void start(Stage stage) throws Exception {
        initComponents();

        VBox mainPane = new VBox();
        mainPane.setId("main");
        mainPane.getChildren().addAll(bc1, bc2, bc3);

        Scene scene = new Scene(mainPane);

        scene.getStylesheets().add("/toolbar/breadcrumbs/style.css");

        stage.setTitle("Demo of styles");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /** Create and setup all the components used in this demo. */
    private void initComponents() {
        // Upper breadcrumbs. Only one element.
        bc1 = new ToolBar();
        bc1.getStyleClass().add("breadcrumbs");

        Button bc1Button = new Button("Alone");
        bc1Button.getStyleClass().addAll("item");
        bc1.getItems().add(bc1Button);

        // Middle breadcrumbs. Two elements.
        bc2 = new ToolBar();
        bc2.getStyleClass().add("breadcrumbs");

        Button bc2Button1 = new Button("First");
        bc2Button1.getStyleClass().addAll("item", "first");

        Button bc2Button2 = new Button("Last");
        bc2Button2.getStyleClass().addAll("item", "last");

        bc2.getItems().addAll(bc2Button1, bc2Button2);

        // Last breadcrumbs. Three elements.
        bc3 = new ToolBar();
        bc3.getStyleClass().add("breadcrumbs");

        Button bc3Button1 = new Button("First");
        bc3Button1.getStyleClass().addAll("item", "first");

        Button bc3Button2 = new Button("Middle");
        bc3Button2.getStyleClass().addAll("item", "middle");

        Button bc3Button3 = new Button("Last");
        bc3Button3.getStyleClass().addAll("item", "last");

        bc3.getItems().addAll(bc3Button1, bc3Button2, bc3Button3);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
