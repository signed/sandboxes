package application;

import application.input.InputPresenter;
import application.input.InputView;
import com.google.inject.Guice;
import com.google.inject.Injector;
import contributionOne.ContributionOneModule;
import contributionTwo.ContributionTwoModule;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ModularJFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final Injector injector = Guice.createInjector(new ApplicationModule(), new ContributionOneModule(), new ContributionTwoModule(), new ToolBarModule());

    @Override
    public void start(Stage stage) throws Exception {
        ToolBar toolBar = new ToolBar();
        for (ToolBarContribution contribution : injector.getInstance(ToolBarContributions.class)) {
            contribution.addTo(toolBar);
        }
        injector.getInstance(InputPresenter.class);

        FlowPane pane = new FlowPane();
        InputView inputView = injector.getInstance(InputView.class);

        pane.getChildren().add(toolBar);
        inputView.addTo(pane);

        stage.setScene(new Scene(pane));
        stage.show();
    }

}
