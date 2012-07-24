package styling;

import com.sun.javafx.css.CssError;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class Forum {
    private final TextArea errorOutput = new TextArea();

    public void clear(){
        errorOutput.clear();
    }

    public void entertainError(CssError error){
        String existingText = errorOutput.getText();
        errorOutput.setText(error.toString() + "\n" + existingText);

    }

    public Node component() {
        return errorOutput;
    }
}
