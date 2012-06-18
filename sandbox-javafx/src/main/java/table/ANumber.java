package table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ANumber {
    private DoubleProperty doubleProperty = new SimpleDoubleProperty();

    public ANumber(Double value) {
        doubleProperty.setValue(value);
    }

    public DoubleProperty doubleProperty() {
        return doubleProperty;
    }
}
