package styling;

import button.MoltenToggleButtonBar;
import com.github.signed.protocols.jvm.InMemoryUrl;
import com.github.signed.protocols.jvm.MemoryDictionary;
import com.sun.javafx.css.StyleHelper;
import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Callback;

import static styling.Family.adapted;

public class CssFun extends Application {

    private final MemoryDictionary memoryDictionary;

    public static void main(String[] args) {
        launch(args);
    }

    private final Forum forum = new Forum();
    private final ShowCase showCase = new ShowCase(400);
    private StyleBook styleBook = new StyleBook();
    private final StylePad stylePad = new StylePad();


    private static Exhibit prepareTextArea() {
        TextArea textArea = new TextArea("Some lyrics\nHip Hop runner von dem Dach");
        textArea.setMaxWidth(250);
        textArea.setMaxHeight(200);
        return new Exhibit(textArea);
    }

    private static Exhibit prepareToggleButton() {
        ToggleButton node = new ToggleButton("Ich bin der Text");
        node.getStyleClass().add("fancy-toggle-button");
        node.getStylesheets().add("jvm://fancy-toggle-button.css");
        return new Exhibit(node);
    }

    private static Exhibit prepareMoltenButtonBarDemo() {
        MoltenToggleButtonBar moltenToggleButtonBar = new MoltenToggleButtonBar();
        moltenToggleButtonBar.addToggleButton("Left Button");
        moltenToggleButtonBar.addToggleButton("Center Button");
        moltenToggleButtonBar.addToggleButton("another");
        moltenToggleButtonBar.addToggleButton("one");
        moltenToggleButtonBar.addToggleButton("Right Button");
        moltenToggleButtonBar.each(new Callback<ToggleButton, Void>() {
            @Override
            public Void call(final ToggleButton toggleButton) {
                toggleButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println(toggleButton.getText());
                    }
                });
                return null;
            }
        });

        HBox hBox = new HBox();
        moltenToggleButtonBar.addButtonsTo(hBox);
        return new Exhibit(hBox);
    }


    private Archivist archivist = new Archivist();
    private final Exhibit exhibit;

    public CssFun() {
        memoryDictionary = new MemoryDictionary();
        StringBuilder builder = new StringBuilder();
        builder.append(".fancy-toggle-button{").append("\n");
        builder.append("-fx-border-color:blue");
        builder.append("}").append("\n");

        memoryDictionary.depose("fancy-toggle-button.css", builder);

        InMemoryUrl.registerInMemoryUrlHandler(memoryDictionary);

        exhibit = prepareToggleButton();
        styleBook.addStyleClasses(exhibit.appliedStyleClasses());

        ToggleButton toggleButton = new ToggleButton("bla bla");
        ObservableList<String> appliedStyleClasses = toggleButton.getStyleClass();
        for (String appliedStyleClass : appliedStyleClasses) {
            System.out.println("class: "+ appliedStyleClass);
        }
        StyleManager styleManager = StyleManager.getInstance();
        StyleHelper styleHelper = styleManager.getStyleHelper(toggleButton);
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
