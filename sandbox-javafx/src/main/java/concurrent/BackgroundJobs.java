package concurrent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.collect.Lists.newArrayList;
import static javafx.concurrent.WorkerStateEvent.WORKER_STATE_CANCELLED;
import static javafx.concurrent.WorkerStateEvent.WORKER_STATE_FAILED;
import static javafx.concurrent.WorkerStateEvent.WORKER_STATE_SUCCEEDED;

public class BackgroundJobs extends Application {

    private final ProgressIndicator progressIndicator = new ProgressIndicator();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] arguments) {
        launch(arguments);
    }

    private final Label messageLabel = new Label("We start in Germany");
    private final Label eventTypeLabel = new Label("empty");
    private final List<Leg> journey = newArrayList(new Leg("France"), new Leg("Spain"), new Leg("Portugal"));
    private final Task<StringWrapper> task = new TravelToPortugal(journey);

    public static class StartEndWorkerAdapter implements EventHandler<WorkerStateEvent> {
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

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("implicit exit on default: "+Platform.isImplicitExit());
        VBox container = new VBox();
        HBox buttonBar = createButtonBar();
        messageLabel.textProperty().bind(task.messageProperty());

        task.addEventHandler(WorkerStateEvent.ANY, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                eventTypeLabel.setText(workerStateEvent.getEventType().getName());
                System.out.println(task.getState());
            }
        });

        task.addEventHandler(WorkerStateEvent.ANY, new StartEndWorkerAdapter() {
            @Override
            protected void started(WorkerStateEvent workerStateEvent) {
                progressIndicator.setVisible(true);
            }

            @Override
            protected void ended(WorkerStateEvent workerStateEvent) {
                progressIndicator.setVisible(false);
            }
        });

        container.getChildren().addAll(buttonBar, messageLabel, eventTypeLabel, progressIndicator);
        progressIndicator.setVisible(false);

        stage.setScene(new Scene(container));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdown();
    }

    private HBox createButtonBar() {
        HBox buttonBar = new HBox();
        Button startTravel = new Button("start background job");
        Button carBreaksDown = new Button("car breaks down");
        buttonBar.getChildren().addAll(startTravel, carBreaksDown);

        startTravel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                runTask(task);
            }
        });

        carBreaksDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                task.cancel();
            }
        });
        return buttonBar;
    }

    private void runTask(Task<StringWrapper> task) {
        executorService.execute(task);
    }

}
