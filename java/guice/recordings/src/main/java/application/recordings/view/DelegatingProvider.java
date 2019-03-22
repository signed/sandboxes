package application.recordings.view;

import com.google.common.base.Function;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DelegatingProvider<S, T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final Function<S, T> function;

    public DelegatingProvider(Function<S, T> function) {
        this.function = function;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> recordingSeriesCellDataFeatures) {
        S value = recordingSeriesCellDataFeatures.getValue();
        return new SimpleObjectProperty<>(function.apply(value));
    }
}
