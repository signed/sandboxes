package application;

import com.google.common.collect.Lists;
import contribution.ContributionOne;
import contribution.JavaFxOneView;
import contribution.OneView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.List;

public class ModularJFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static interface Closure {
        void execute();
    }

    @Override
    public void start(Stage stage) throws Exception {
        List<ToolBarContribution> contributions = Lists.newArrayList();
        ApplicationModel applicationModel = new ApplicationModel();
        OneView oneView = new JavaFxOneView();
        contributions.add(new ContributionOne(oneView, applicationModel));

        ToolBar toolBar = new ToolBar();
        for (ToolBarContribution contribution : contributions) {
            contribution.addTo(toolBar);
        }

        FlowPane pane = new FlowPane();
        pane.getChildren().add(toolBar);

        stage.setScene(new Scene(pane));
        stage.show();
    }
}
