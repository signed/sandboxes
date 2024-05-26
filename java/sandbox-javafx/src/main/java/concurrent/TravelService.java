package concurrent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class TravelService extends Service<TravelReport>{
    private final ObservableList<Leg> journey = FXCollections.observableArrayList();

    public ObservableList<Leg> journeyProperty() {
        return journey;
    }

    @Override
    protected Task<TravelReport> createTask() {
        return new TravelToPortugal(journey);
    }
}
