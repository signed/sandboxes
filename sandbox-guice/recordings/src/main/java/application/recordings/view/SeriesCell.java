package application.recordings.view;

import domain.Recording;
import domain.Series;

public class SeriesCell extends SafeTableCell<Recording, Series> {
    @Override
    protected void updateSafely(Series series) {
        setText(series.name);
    }
}
