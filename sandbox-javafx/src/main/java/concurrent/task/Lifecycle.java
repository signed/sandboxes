package concurrent.task;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lifecycle extends Application {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Task<Void> theTask = new TheTask();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        dumpTaskState();

        theTask.addEventHandler(WorkerStateEvent.ANY, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                    dumpTaskState();
            }
        });

        stage.setScene(new Scene(new Button("nothing to see, walk along ...")));
        stage.show();

        executorService.submit(theTask);
    }

    private void dumpTaskState() {
        System.out.println(theTask.stateProperty().getValue());
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdown();
    }

}
