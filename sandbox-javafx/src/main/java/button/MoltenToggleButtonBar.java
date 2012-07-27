package button;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class MoltenToggleButtonBar {
    private static final String MoltenButtonBar = MoltenToggleButtonBar.class.getResource("MoltenButtonBar.css").toExternalForm();

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final List<ToggleButton> buttons = new ArrayList<>();
    private final Group limitHBox = new Group();
    private final HBox container = new HBox();

    public ToggleButton addToggleButton(String text) {
        ToggleButton toggleButton = new ToggleButton(text);
        addToggleButton(toggleButton);
        return toggleButton;
    }

    public void addToggleButton(ToggleButton button) {
        buttons.add(button);
    }

    public void addButtonsTo(Pane pane) {
        addInternal(container);
        limitHBox.getChildren().add(container);
        pane.getChildren().add(limitHBox);
    }

    private void addInternal(Pane pane) {
        ToggleButton first = buttons.get(0);
        List<ToggleButton> centerButtons = new ArrayList<>();
        if(buttons.size()>2){
            for(int centerButtonIndex = 1; centerButtonIndex <= buttons.size() -2; ++centerButtonIndex) {
                centerButtons.add(buttons.get(centerButtonIndex));
            }
        }
        ToggleButton last = buttons.get(buttons.size() - 1);

        applyStyleTo("molten-button-bar-button-left", first);
        for (ToggleButton button : centerButtons) {
            applyStyleTo("molten-button-bar-button-middle", button);
        }
        applyStyleTo("molten-button-bar-button-right", last);

        for(ToggleButton button: buttons){
            button.setToggleGroup(toggleGroup);
            button.addEventFilter(MouseEvent.MOUSE_PRESSED, new SuppressDeselection(button));
            pane.getChildren().add(button);
        }
        pane.getStylesheets().add(MoltenButtonBar);
    }

    public void each(Callback<ToggleButton, Void> callback){
        for (ToggleButton button : buttons) {
            callback.call(button);
        }
    }

    private void applyStyleTo(String style, ToggleButton button) {
        button.getStyleClass().add(style);
    }

    private static class SuppressDeselection implements EventHandler<MouseEvent> {
        private final ToggleButton button;

        public SuppressDeselection(ToggleButton button) {
            this.button = button;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if(button.isSelected()){
                mouseEvent.consume();
            }
        }
    }
}
