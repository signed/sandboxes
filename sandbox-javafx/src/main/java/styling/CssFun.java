package styling;

import com.github.signed.protocols.jvm.InMemoryUrl;
import com.github.signed.protocols.jvm.MemoryDictionary;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.nio.file.Path;
import java.nio.file.Paths;

import static styling.Family.adapted;

public class CssFun extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final MemoryDictionary memoryDictionary = new MemoryDictionary();
    private final Path pathToStyleFile = Paths.get("style.css");
    private final Path pathToStylesheetFile = Paths.get("stylesheet.css");
    private final Forum forum = new Forum();
    private final StyleCritic critic = new StyleCritic(forum);
    private final ShowCase showCase = new ShowCase(400);
    private final StyleInputs styleInputs = new StyleInputs();
    private final StylePad stylePad = new StylePad();
    private final StylePad styleSheetPad = new StylePad();
    private final Archivist archivist = new Archivist();
    private final Exhibit exhibit;

    public CssFun() {
        InMemoryUrl.registerInMemoryUrlHandler(memoryDictionary);
        exhibit = new ExhibitBuilder().prepareMoltenButtonBarDemo();
        //exhibit = new ExhibitBuilder().prepareTextArea();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFx css tester");
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        stylePad.onChange(new Stylist(new DirectlyStyleComponent(exhibit)));
        stylePad.onError(critic);

        styleSheetPad.onChange(new Stylist(new StyleOverStylesheet(exhibit, memoryDictionary)));
        styleSheetPad.onError(critic);

        showCase.display(exhibit);

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);


        styleInputs.integrate(stylePad, "style");
        styleInputs.integrate(styleSheetPad, "stylesheet");
        styleInputs.integrateInto(adapted(splitPane));
        showCase.integrateInto(adapted(splitPane));

        ObservableList<Node> children = splitPane.getItems();
        HBox.setHgrow(children.get(children.size() - 1), Priority.SOMETIMES);
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(splitPane);
        mainPane.setBottom(forum.component());
        Button button = new Button("generate template for exhibit");
        button.setOnAction(new StyleClassTemplateWriter(exhibit, styleSheetPad));
        mainPane.setTop(button);

        Scene scene = new Scene(mainPane);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new Magnifier(exhibit));
        stage.setScene(scene);
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new TurnInTranscript(stylePad, archivist, pathToStyleFile));
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new TurnInTranscript(styleSheetPad, archivist, pathToStylesheetFile));
        stage.show();
        stage.setFullScreen(true);
        stage.setFullScreen(false);

        initializeStylePadFrom(pathToStyleFile, stylePad);
        initializeStylePadFrom(pathToStylesheetFile, styleSheetPad);
    }

    private void initializeStylePadFrom(Path path, StylePad pad) {
        String style = archivist.retrieveStyleFrom(path);
        pad.showStyle(style);
    }

}
