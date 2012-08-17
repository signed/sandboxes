package styling;

import com.github.signed.protocols.jvm.InMemoryUrl;
import com.github.signed.protocols.jvm.MemoryDictionary;
import com.google.common.collect.Lists;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.nio.file.Path;
import java.nio.file.Paths;

import static styling.Family.adapted;

public class CssFun extends Application {

    private final ObservableList<Exhibit> exhibits;

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
    private Exhibit exhibit;

    private Property<Exhibit> exhibitProperty = new SimpleObjectProperty<>();

    public CssFun() {
        ExhibitBuilder builder = new ExhibitBuilder();
        Exhibit first = builder.prepareTextArea();
        Exhibit second = builder.prepareToggleButton();
        Exhibit third = builder.prepareMoltenButtonBarDemo();
        exhibits = new ObservableListWrapper<>(Lists.newArrayList(first, second, third));
        InMemoryUrl.registerInMemoryUrlHandler(memoryDictionary);

        exhibit = exhibits.get(0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFx css tester");
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        final DirectlyStyleComponent directlyStyleComponent = new DirectlyStyleComponent(exhibit);
        final StyleOverStylesheet styleOverStylesheet = new StyleOverStylesheet(exhibit, memoryDictionary);
        final StyleClassTemplateWriter styleClassTemplateWriter = new StyleClassTemplateWriter(exhibit, styleSheetPad);
        final Magnifier magnifier = new Magnifier(exhibit);
        exhibitProperty.addListener(new ChangeListener<Exhibit>() {
            @Override
            public void changed(ObservableValue<? extends Exhibit> observableValue, Exhibit exhibit, Exhibit newSelectedExhibit) {
                showCase.display(newSelectedExhibit);
                directlyStyleComponent.actOn(newSelectedExhibit);
                styleOverStylesheet.actOn(newSelectedExhibit);
                styleClassTemplateWriter.actOn(newSelectedExhibit);
                magnifier.actOn(newSelectedExhibit);
            }
        });

        stylePad.onChange(new Stylist(directlyStyleComponent));
        stylePad.onError(critic);

        styleSheetPad.onChange(new Stylist(styleOverStylesheet));
        styleSheetPad.onError(critic);

        //showCase.display(exhibit);

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

        mainPane.setTop(createTopBar(styleClassTemplateWriter));

        Scene scene = new Scene(mainPane);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, magnifier);
        stage.setScene(scene);
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new TurnInTranscript(stylePad, archivist, pathToStyleFile));
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new TurnInTranscript(styleSheetPad, archivist, pathToStylesheetFile));
        stage.show();
        stage.setFullScreen(true);
        stage.setFullScreen(false);

        initializeStylePadFrom(pathToStyleFile, stylePad);
        initializeStylePadFrom(pathToStylesheetFile, styleSheetPad);

        exhibitProperty.setValue(exhibit);
    }

    private HBox createTopBar(StyleClassTemplateWriter styleClassTemplateWriter) {
        ComboBox<Exhibit> availableExhibits = new ComboBox<>();
        Callback<ListView<Exhibit>, ListCell<Exhibit>> cellFactory = new Callback<ListView<Exhibit>, ListCell<Exhibit>>() {
            @Override
            public ListCell<Exhibit> call(ListView<Exhibit> exhibitListView) {
                return new ListCell<Exhibit>() {
                    @Override
                    protected void updateItem(Exhibit exhibit, boolean b) {
                        super.updateItem(exhibit, b);
                        if (null == exhibit) return;
                        setText(exhibit.getName());
                    }
                };
            }
        };
        availableExhibits.setCellFactory(cellFactory);
        availableExhibits.setButtonCell(cellFactory.call(null));
        availableExhibits.setItems(exhibits);
        availableExhibits.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Exhibit>() {
            @Override
            public void changed(ObservableValue<? extends Exhibit> observableValue, Exhibit exhibit, Exhibit exhibit1) {
                exhibitProperty.setValue(exhibit1);
            }
        });
        HBox topHBox = new HBox();
        Button createTemplateButton = new Button("generate template for exhibit");
        createTemplateButton.setOnAction(styleClassTemplateWriter);
        topHBox.getChildren().addAll(availableExhibits, createTemplateButton);
        return topHBox;
    }

    private void initializeStylePadFrom(Path path, StylePad pad) {
        String style = archivist.retrieveStyleFrom(path);
        pad.showStyle(style);
    }

}
