package styling;

import button.MoltenToggleButtonBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
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
