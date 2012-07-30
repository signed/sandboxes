package concurrent;

import javafx.application.Application;
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

public class BackgroundJobs extends Application {

    public static void main(String[] arguments) {
        launch(arguments);
    }

    private final Label messageLabel = new Label("We start in Germany");
    private final Label eventTypeLabel = new Label("empty");

    private final List<Leg> journey = newArrayList(new Leg("France"), new Leg("Spain"), new Leg("Portugal"));
    private final Task<TravelReport> task = new TravelToPortugal(journey);
    private final ProgressIndicator progressIndicator = new ProgressIndicator();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final TravelService travelService = new TravelService();


    public static interface EventSource {
        <T extends Event> void addEventHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox container = new VBox();
        HBox buttonBar = createButtonBar();
        messageLabel.textProperty().bind(task.messageProperty());

        Task<TravelReport> worker = task;

        worker.addEventHandler(WorkerStateEvent.ANY, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                eventTypeLabel.setText(workerStateEvent.getEventType().getName());
                System.out.println(task.getState());
            }
        });

        worker.addEventHandler(WorkerStateEvent.ANY, new StartEndWorkerAdapter() {
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
                executeSingleTask();
            }
        });

        carBreaksDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stopExecution();
            }
        });
        return buttonBar;
    }

    private void stopExecution() {
        task.cancel();
    }

    private void executeSingleTask() {
        executorService.execute(task);
    }
}
