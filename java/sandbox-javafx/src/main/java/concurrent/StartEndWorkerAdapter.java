package concurrent;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

import static javafx.concurrent.WorkerStateEvent.WORKER_STATE_CANCELLED;
import static javafx.concurrent.WorkerStateEvent.WORKER_STATE_FAILED;
import static javafx.concurrent.WorkerStateEvent.WORKER_STATE_SUCCEEDED;

public class StartEndWorkerAdapter implements EventHandler<WorkerStateEvent> {
    private boolean started = false;
    private boolean ended = false;

    @Override
    public void handle(WorkerStateEvent workerStateEvent) {
        EventType<? extends Event> type = workerStateEvent.getEventType();
        boolean isBackGroundComputationDone = type == WORKER_STATE_CANCELLED || type == WORKER_STATE_FAILED || type == WORKER_STATE_SUCCEEDED;
        boolean backGroupJobRunning = !isBackGroundComputationDone;

        if(backGroupJobRunning && !started){
            started = true;
            this.started(workerStateEvent);
        }else if(isBackGroundComputationDone && started && !ended) {
            ended = true;
            this.ended(workerStateEvent);
        }
    }

    protected  void started(WorkerStateEvent workerStateEvent){
        //nothing to do
    }

    protected  void ended(WorkerStateEvent workerStateEvent){
        //nothing to do
    }
}
