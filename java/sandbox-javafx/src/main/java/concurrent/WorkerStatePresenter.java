package concurrent;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class WorkerStatePresenter implements EventHandler<WorkerStateEvent> {
    private Label target;

    public WorkerStatePresenter(Label target) {

        this.target = target;
    }

    @Override
    public void handle(WorkerStateEvent workerStateEvent) {
        target.setText(workerStateEvent.getEventType().getName());
    }
}
