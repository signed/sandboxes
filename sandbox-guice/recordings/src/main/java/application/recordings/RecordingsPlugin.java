package application.recordings;

import application.recordings.view.RecordingsView;
import extensionpoints.ViewContribution;
import javafx.scene.layout.Pane;

public class RecordingsPlugin  implements ViewContribution{
    private final RecordingsView view = new RecordingsView();
    private final RecordingsModel model = new RecordingsModel();
    private final RecordingsPresenter presenter;

    public RecordingsPlugin() {
        presenter = new RecordingsPresenter(model, view);
        presenter.wireModelAndView();
    }

    @Override
    public void addTo(Pane pane) {
        view.addTo(pane);
    }
}