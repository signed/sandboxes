package application.recordings.view;

import application.input.SubordinatingView;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sun.javafx.collections.ObservableListWrapper;
import domain.Episode;
import domain.Recording;
import domain.Season;
import domain.Series;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.joda.time.DateTime;

import java.util.Collection;

public class RecordingsView implements SubordinatingView {

    private final TableView<Recording> table = new TableView<>();
    private final TableColumn<Recording, Series> seriesColumn = new TableColumn<>();
    private final TableColumn<Recording, Season> seasonColumn = new TableColumn<>();
    private final TableColumn<Recording, Episode> episodeColumn = new TableColumn<>();
    private final TableColumn<Recording, DateTime> airedColumn = new TableColumn<>();


    public RecordingsView() {
        seriesColumn.setCellValueFactory(new DelegatingProvider<>(new Function<Recording, Series>() {

            @Override
            public Series apply(Recording input) {
                return input.series;
            }
        }));
        seriesColumn.setCellFactory(new Callback<TableColumn<Recording, Series>, TableCell<Recording, Series>>() {
            @Override
            public TableCell<Recording, Series> call(TableColumn<Recording, Series> recordingSeriesTableColumn) {
                return new SeriesCell();
            }
        });
        seriesColumn.setText("series");

        seasonColumn.setCellValueFactory(new DelegatingProvider<>(new Function<Recording, Season>() {
            @Override
            public Season apply(Recording input) {
                return input.season;
            }
        }));
        seasonColumn.setCellFactory(new Callback<TableColumn<Recording, Season>, TableCell<Recording, Season>>() {
            @Override
            public TableCell<Recording, Season> call(TableColumn<Recording, Season> recordingSeasonTableColumn) {
                return new SeasonCell();
            }
        });
        seasonColumn.setText("season");

        episodeColumn.setCellValueFactory(new DelegatingProvider<>(new Function<Recording, Episode>() {
            @Override
            public Episode apply(Recording input) {
                return input.episode;
            }
        }));
        episodeColumn.setCellFactory(new Callback<TableColumn<Recording, Episode>, TableCell<Recording, Episode>>() {
            @Override
            public TableCell<Recording, Episode> call(TableColumn<Recording, Episode> recordingEpisodeTableColumn) {
                return new EpisodeTableCell();
            }
        });
        episodeColumn.setText("episode");

        airedColumn.setCellValueFactory(new DelegatingProvider<>(new Function<Recording, DateTime>() {
            @Override
            public DateTime apply( Recording input) {
                return input.aired;
            }
        }));
        airedColumn.setCellFactory(new Callback<TableColumn<Recording, DateTime>, TableCell<Recording, DateTime>>() {
            @Override
            public TableCell<Recording, DateTime> call(TableColumn<Recording, DateTime> recordingDateTimeTableColumn) {
                return new AirDateCell();
            }
        });
        airedColumn.setText("airtime");

        table.getColumns().add(seriesColumn);
        table.getColumns().add(seasonColumn);
        table.getColumns().add(episodeColumn);
        table.getColumns().add(airedColumn);
    }


    public void display(Collection<Recording> recordings) {
        table.setItems(new ObservableListWrapper<>(Lists.newArrayList(recordings)));
    }

    @Override
    public void addTo(Pane pane) {
        pane.getChildren().add(table);
    }
}
