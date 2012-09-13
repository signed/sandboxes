package application.recordings.view;

import javafx.scene.control.TableCell;

public abstract class SafeTableCell<S, T> extends TableCell<S, T> {

    @Override
    protected void updateItem(T t, boolean b) {
        super.updateItem(t, b);
        if (null == t) {
            return;
        }
        updateSafely(t);
    }

    protected abstract void updateSafely(T t);

}
