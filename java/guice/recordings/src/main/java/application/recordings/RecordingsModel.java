package application.recordings;

import com.google.common.collect.Lists;
import domain.Episode;
import domain.Recording;
import domain.Season;
import domain.Series;
import lang.ArgumentClosure;
import org.joda.time.DateTime;

import java.util.List;

public class RecordingsModel {

    private final List<Recording> recordings = Lists.newArrayList();

    public RecordingsModel() {
        recordings.add(new Recording(new Season(1), new Episode(1), new Series("Scrubs"), new DateTime(2001, 10,2,0,0,0)));
        recordings.add(new Recording(new Season(1), new Episode(2), new Series("Scrubs"), new DateTime(2001, 10,4,0,0,0)));
        recordings.add(new Recording(new Season(1), new Episode(3), new Series("Scrubs"), new DateTime(2001, 10,9,0,0,0)));
        recordings.add(new Recording(new Season(1), new Episode(4), new Series("Scrubs"), new DateTime(2001, 10,16,0,0,0)));

        recordings.add(new Recording(new Season(2), new Episode(1), new Series("Scrubs"), new DateTime(2002, 9,26,0,0,0)));
        recordings.add(new Recording(new Season(2), new Episode(2), new Series("Scrubs"), new DateTime(2002, 10,3,0,0,0)));


        recordings.add(new Recording(new Season(1), new Episode(1), new Series("House"), new DateTime(2004, 10,16,0,0,0)));
        recordings.add(new Recording(new Season(1), new Episode(2), new Series("House"), new DateTime(2004, 10,23,0,0,0)));
        recordings.add(new Recording(new Season(1), new Episode(22), new Series("House"), new DateTime(2005, 5,24,0,0,0)));

        recordings.add(new Recording(new Season(2), new Episode(1), new Series("House"), new DateTime(2005, 9,13,0,0,0)));
        recordings.add(new Recording(new Season(2), new Episode(2), new Series("House"), new DateTime(2005, 9,20,0,0,0)));


    }

    public void provideTo(ArgumentClosure<List<Recording>> sink){
        sink.execute(recordings);
    }
}
