package styling;

import com.github.signed.protocols.jvm.InMemoryUrl;
import com.github.signed.protocols.jvm.MemoryDictionary;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import static styling.Family.adapted;

public class CssFun extends Application {

    private final MemoryDictionary memoryDictionary = new MemoryDictionary();

    public static void main(String[] args) {
        launch(args);
    }

    private final Forum forum = new Forum();
    private final StyleCritic critic = new StyleCritic(forum);
    private final ShowCase showCase = new ShowCase(400);

    private final StyleInputs styleInputs = new StyleInputs();
    private final StylePad stylePad = new StylePad();
    private final StylePad styleSheetPad = new StylePad();


    private Archivist archivist = new Archivist();
    private final Exhibit exhibit;

    public CssFun() {
        InMemoryUrl.registerInMemoryUrlHandler(memoryDictionary);
        exhibit = new ExhibitBuilder().prepareToggleButton();
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

        String style = archivist.retrieveStyle();
        stylePad.showStyle(style);


        Scene scene = new Scene(mainPane);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new Magnifier(exhibit));
        stage.setScene(scene);
        stage.onCloseRequestProperty().set(new TurnInTranscript(archivist, stylePad));
        stage.show();
        stage.setFullScreen(true);
        stage.setFullScreen(false);
    }
}
