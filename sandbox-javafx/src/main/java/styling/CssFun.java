package styling;

import com.github.signed.protocols.jvm.InMemoryUrl;
import com.github.signed.protocols.jvm.MemoryDictionary;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import static styling.Family.adapted;

public class CssFun extends Application {

    private final MemoryDictionary memoryDictionary;

    public static void main(String[] args) {
        launch(args);
    }

    private final Forum forum = new Forum();
    private final ShowCase showCase = new ShowCase(400);
    private final StylePad stylePad = new StylePad();


    private Archivist archivist = new Archivist();
    private final Exhibit exhibit;

    public CssFun() {
        memoryDictionary = new MemoryDictionary();
        deposeStyleWithBorderColor("blue");
        InMemoryUrl.registerInMemoryUrlHandler(memoryDictionary);
        exhibit = new ExhibitBuilder().prepareToggleButton();
        exhibit.useInMemoryStyleSheetAt("jvm://fancy-toggle-button.css");
        exhibit.reApplyInMemoryStyleSheet();
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

        TabPane tabPane = new TabPane();
        ObservableList<Tab> tabs = tabPane.getTabs();
        Tab styleTab = new Tab("style");
        tabs.add(styleTab);

        splitPane.getItems().add(tabPane);

        stylePad.integrateInto(adapted(styleTab));
        showCase.integrateInto(adapted(splitPane));
        ObservableList<Node> children = splitPane.getItems();
        HBox.setHgrow(children.get(children.size() - 1), Priority.SOMETIMES);
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(splitPane);
        mainPane.setBottom(forum.component());
        Button button = new Button("apply new style class");
        button.rotateProperty().set(-45);
        button.setOnAction(new updateInMemoryStyleSheet());
        mainPane.setLeft(button);


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

    private void deposeStyleWithBorderColor(String color) {
        StringBuilder builder = createBorderInColor(color);
        memoryDictionary.depose("fancy-toggle-button.css", builder);
    }

    private StringBuilder createBorderInColor(String color) {
        StringBuilder builder = new StringBuilder();
        builder.append(".fancy-toggle-button{").append("\n");
        builder.append("-fx-border-color:" + color + ";");
        builder.append("}").append("\n");
        return builder;
    }

    private class updateInMemoryStyleSheet implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            deposeStyleWithBorderColor("red");
            exhibit.reApplyInMemoryStyleSheet();
        }
    }
}
