package styling;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Stylist implements ChangeListener<String> {
    private final PartToStyle partToStyle;

    public Stylist(PartToStyle partToStyle) {
        this.partToStyle = partToStyle;
    }

    public static interface PartToStyle {
        void styleWith(String style);
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
        partToStyle.styleWith(s1);
    }
}
