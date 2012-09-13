package application.recordings.view;

import domain.Episode;
import domain.Recording;

import static java.lang.String.format;

class EpisodeTableCell extends SafeTableCell<Recording, Episode> {
    @Override
    protected void updateSafely(Episode episode) {
        setText(format("E%02d", episode.number));
    }
}
