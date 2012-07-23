package styling;

import com.sun.javafx.css.CssError;
import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.parser.CSSParser;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class CssFun extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final StyleManager styleManger = StyleManager.getInstance();
    private final CSSParser parser = CSSParser.getInstance();
    private final Node styleable = new ToggleButton("Ich bin der Text");
    private final TextArea errorOutput = new TextArea();


    @Override
    public void start(Stage stage) throws Exception {


        HBox workbench = createWorkbenchPane();
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(workbench);
        mainPane.setBottom(errorOutput);
        stage.setScene(new Scene(mainPane));
        stage.show();
    }

    private HBox createWorkbenchPane() {
        TextArea styleInput = new TextArea();

        styleManger.errorsProperty().addListener(new ListChangeListener<CssError>() {
            @Override
            public void onChanged(Change<? extends CssError> change) {
                while(change.next()){
                    List<? extends CssError> added = change.getAddedSubList();
                    for (CssError cssError : added) {

                        String existingText = errorOutput.getText();
                        errorOutput.setText(cssError.toString() + "\n" + existingText);
                    }
                }
            }
        });

        styleInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                //errorOutput.clear();
                parser.parse(s1);
                styleable.setStyle(s1);
            }
        });

        styleInput.setText("-fx-base: lightgreen;");

        BorderPane componentPane = new BorderPane();
        componentPane.setCenter(styleable);

        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(styleInput, componentPane);
        return mainLayout;
    }
}
