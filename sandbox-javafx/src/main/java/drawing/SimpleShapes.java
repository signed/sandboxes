package drawing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.EllipseBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import multimonitor.CenterOnScreenTwo;
import stage.EnsureWindowComesOnTop;

public class SimpleShapes extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnShowing(new CenterOnScreenTwo());
        stage.setOnShown(new EnsureWindowComesOnTop());

        HBox hBox = new HBox();
        Ellipse ellipse = EllipseBuilder.create().radiusX(8).radiusY(8).strokeWidth(2).stroke(Color.BLACK).fill(null).build();
        Text text = TextBuilder.create().stroke(Color.LIGHTGREEN).text("t").build();
        Label label = new Label("one");
        hBox.getChildren().addAll(label, ellipse, text);
        stage.setScene(new Scene(hBox));

        stage.show();
    }

}
