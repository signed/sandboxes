package concurrent;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.ProgressIndicator;

public class ProgressPresenter extends StartEndWorkerAdapter {
    private final ProgressIndicator progressIndicator;

    public ProgressPresenter(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    @Override
    protected void started(WorkerStateEvent workerStateEvent) {
        progressIndicator.setVisible(true);
    }

    @Override
    protected void ended(WorkerStateEvent workerStateEvent) {
        progressIndicator.setVisible(false);
    }
}
