package styling;

import com.sun.javafx.css.CssError;
import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.parser.CSSParser;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CssFun extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final StyleManager styleManger = StyleManager.getInstance();
    private final CSSParser parser = CSSParser.getInstance();
    private final Node styleable = new ToggleButton("Ich bin der Text");
    private final TextArea styleInput = new TextArea();
    private final TextArea errorOutput = new TextArea();
    private final Path styleFile = Paths.get("style.css");
    final BorderPane componentHolderPane = new BorderPane();


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFx css tester");
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        String style = loadStyleFromLastRun();

        HBox workbench = createWorkbenchPane(style);
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(workbench);
        mainPane.setBottom(errorOutput);


        Scene scene = new Scene(mainPane);


        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                double currentScale = componentHolderPane.getScaleX();
                double newScale = currentScale;

                KeyCode pressedKey = keyEvent.getCode();
                if(KeyCode.PLUS == pressedKey || KeyCode.ADD == pressedKey){
                    newScale = currentScale +0.25;
                }else if(KeyCode.MINUS == pressedKey || KeyCode.SUBTRACT == pressedKey){
                    newScale = currentScale -0.25;
                }else if(KeyCode.DIGIT0 == pressedKey ||KeyCode.NUMPAD0 == pressedKey){
                    newScale = 1.0;
                }
                componentHolderPane.scaleXProperty().set(newScale);
                componentHolderPane.scaleYProperty().set(newScale);
            }
        });


        stage.setScene(scene);

        stage.onCloseRequestProperty().set(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                String[] lines = styleInput.getText().split("\n");
                ArrayList<String> strings = new ArrayList<String>();

                Collections.addAll(strings, lines);
                try {
                    Files.write(styleFile, strings, Charset.forName("UTF-8"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        stage.show();
        stage.setFullScreen(true);
        stage.setFullScreen(false);
    }

    private String loadStyleFromLastRun() throws IOException {
        String style;
        if (Files.exists(styleFile)) {
            List<String> strings = Files.readAllLines(styleFile, Charset.forName("UTF-8"));
            StringBuilder all = new StringBuilder();
            for (String string : strings) {
                all.append(string).append("\n");
            }
            style = all.toString();
        } else {
            Files.createFile(styleFile);
            style = "";
        }
        return style;
    }

    private HBox createWorkbenchPane(String style) {
        styleManger.errorsProperty().addListener(new ListChangeListener<CssError>() {
            @Override
            public void onChanged(Change<? extends CssError> change) {
                errorOutput.clear();
                while (change.next()) {
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
                parser.parse(s1);
                styleable.setStyle(s1);
            }
        });

        styleInput.setText(style);

        componentHolderPane.setCenter(styleable);

        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(styleInput, componentHolderPane);
        HBox.setHgrow(componentHolderPane, Priority.ALWAYS);
        return mainLayout;
    }
}
