package domain;

import org.joda.time.DateTime;

public class Recording {
    public final Season season;
    public final Episode episode;
    public final Series series;
    public final DateTime aired;

    public Recording(Season season, Episode episode, Series series, DateTime aired) {
        this.season = season;
        this.episode = episode;
        this.series = series;
        this.aired = aired;
    }
}
