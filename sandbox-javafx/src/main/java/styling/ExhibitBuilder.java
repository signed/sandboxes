package styling;

import button.MoltenToggleButtonBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ExhibitBuilder {

    public Exhibit prepareTextArea() {
        TextArea textArea = new TextArea("Some lyrics\nHip Hop runner von dem Dach");
        textArea.setMaxWidth(250);
        textArea.setMaxHeight(200);
        return new Exhibit("TextArea", textArea);
    }

    public Exhibit prepareToggleButton() {
        ToggleButton node = new ToggleButton("Ich bin der Text");
        node.getStyleClass().add("fancy-toggle-button");
        return new Exhibit("ToggleButton", node);
    }

    public Exhibit prepareLabel() {
        Label label = new Label("thread-1");
        return new Exhibit("label with text", label);
    }


    public Exhibit prepareLabelForLogLevel() {
        VBox box = new VBox();
        Label trace = createLogLabel("t", "trace");
        Label debug = createLogLabel("d", "debug");
        Label info = createLogLabel("i", "info");
        Label warn = createLogLabel("w", "warn");
        Label error = createLogLabel("e", "error");
        Label fatal = createLogLabel("f", "fatal");

        box.getChildren().addAll(trace, debug, info, warn, error, fatal);

        return new Exhibit("Log Level", box);
    }

    private Label createLogLabel(String type, String colorLevel) {

        Label label = new Label(type);
        label.getStyleClass().add("log-level");
        label.getStyleClass().add(colorLevel);
        //label.prefWidthProperty().bind(label.heightProperty());
        label.minWidthProperty().setValue(57);
        //label.maxWidthProperty().bind(label.heightProperty());
        return label;
    }

    public Exhibit prepareMoltenButtonBarDemo() {
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

        HBox box = new HBox();
        moltenToggleButtonBar.addButtonsTo(box);
        return new Exhibit("MoltenButtonBar", box.getChildren().get(0));
    }
}
