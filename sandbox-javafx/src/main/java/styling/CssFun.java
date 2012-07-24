package styling;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import static styling.Family.adapted;

public class CssFun extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final Forum forum = new Forum();
    private final ShowCase showCase = new ShowCase(400);
    private final StylePad stylePad = new StylePad();


    private static Exhibit prepareTextArea() {
        TextArea textArea = new TextArea("Some lyrics\nHip Hop runner von dem Dach");
        textArea.setMaxWidth(250);
        textArea.setMaxHeight(200);
        return new Exhibit(textArea);
    }

    private static Exhibit prepareToggleButton() {
        return new Exhibit(new ToggleButton("Ich bin der Text"));
    }

    private Archivist archivist = new Archivist();
    private final Exhibit exhibit;

    public CssFun() {
        exhibit = prepareTextArea();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFx css tester");
        stage.setMinWidth(800);
        stage.setMinHeight(600);


        stylePad.onChange(new Stylist(exhibit));
        stylePad.onError(new StyleCritic(forum));
        showCase.display(exhibit);

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        stylePad.integrateInto(adapted(splitPane));
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
