package styling;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

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

    private static Exhibit prepareToggleButtion() {
        return new Exhibit(new ToggleButton("Ich bin der Text"));
    }

    private Archivist archivist = new Archivist();
    private Exhibit exhibit;



    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFx css tester");
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        exhibit = prepareTextArea();

        stylePad.onChange(new Stylist(exhibit));
        stylePad.onError(new StyleCritic(forum));
        showCase.display(exhibit);
        HBox mainLayout = new HBox();
        stylePad.integrateInto(mainLayout);
        showCase.integrateInto(mainLayout);
        ObservableList<Node> children = mainLayout.getChildren();
        HBox.setHgrow(children.get(children.size() - 1), Priority.SOMETIMES);
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(mainLayout);
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
