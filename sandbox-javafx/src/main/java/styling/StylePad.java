package styling;

import com.sun.javafx.css.CssError;
import com.sun.javafx.css.StyleManager;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TextArea;

public class StylePad {
    private final StyleManager styleManger = StyleManager.getInstance();
    private final TextArea styleInput = new TextArea();

    public StylePad() {
        styleInput.setPrefWidth(300);
    }

    public void integrateInto(NodeContainer pane) {
        pane.add(styleInput);
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
