package application.recordings.view;

import domain.Recording;
import domain.Season;

import static java.lang.String.format;

public class SeasonCell extends SafeTableCell<Recording, Season> {
    @Override
    protected void updateSafely(Season season) {
        setText(format("S%02d", season.number));
    }
}
