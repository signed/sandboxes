package styling;

import com.sun.javafx.css.CssError;
import com.sun.javafx.css.StyleManager;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class StylePad {
    private final StyleManager styleManger = StyleManager.getInstance();
    private final TextArea styleInput = new TextArea();

    public StylePad() {
        styleInput.setMaxWidth(300);
    }

    public void integrateInto(Pane pane) {
        pane.getChildren().add(styleInput);
    }

    public void showStyle(String style){
        styleInput.setText(style);
    }

    public void onChange(ChangeListener<String> changeListener) {
        styleInput.textProperty().addListener(changeListener);
    }

    public void onError(ListChangeListener<CssError> changeListener){
        styleManger.errorsProperty().addListener(changeListener);
    }

    public String getText() {
        return styleInput.getText();
    }
}
