package concurrent;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import static concurrent.WorthToObserver.adapt;

public class BackgroundJobs extends Application {

    public static void main(String[] arguments) {
        launch(arguments);
    }

    private final Label messageLabel = new Label("We start in Germany");
    private final Label eventTypeLabel = new Label("empty");

    private final List<Leg> journey = newArrayList(new Leg("France"), new Leg("Spain"), new Leg("Portugal"));
    private final ProgressIndicator progressIndicator = new ProgressIndicator();
    private final Task<TravelReport> task = new TravelToPortugal(journey);
    private final TravelService travelService = new TravelService();

    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final Starter taskStarter = new TaskStarter(task, executor);
    private final Starter serviceStarter = new ServiceStarter(travelService);

    private final BackgroundExecution service = new BackgroundExecution(adapt(travelService), serviceStarter, travelService);
    private final BackgroundExecution singleTask = new BackgroundExecution(adapt(task), taskStarter, task);

    private final BackgroundExecution actual = service;

    @Override
    public void start(Stage stage) throws Exception {
        travelService.setExecutor(executor);

        VBox container = new VBox();
        HBox buttonBar = createButtonBar();

        messageLabel.textProperty().bind(actual.messageProperty());

        WorkerStatePresenter workerStatePresenter = new WorkerStatePresenter(eventTypeLabel);
        ProgressPresenter progressPresenter = new ProgressPresenter(progressIndicator);

        actual.addEventHandler(WorkerStateEvent.ANY, workerStatePresenter);
        actual.addEventHandler(WorkerStateEvent.ANY, progressPresenter);

        container.getChildren().addAll(buttonBar, messageLabel, eventTypeLabel, progressIndicator);
        progressIndicator.setVisible(false);

        stage.setScene(new Scene(container));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        executor.shutdown();
    }

    private HBox createButtonBar() {
        HBox buttonBar = new HBox();
        Button startTravel = new Button("start background job");
        Button carBreaksDown = new Button("car breaks down");
        buttonBar.getChildren().addAll(startTravel, carBreaksDown);
        startTravel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                runIt();
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
        actual.cancel();
    }

    public void runIt() {
        travelService.journeyProperty().clear();
        travelService.journeyProperty().addAll(journey);
        actual.start();
    }

}