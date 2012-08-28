package application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import contribution.ContributionOneModule;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ModularJFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static interface Closure {
        void execute();
    }

    private final Injector injector = Guice.createInjector(new ApplicationModule(), new ContributionOneModule());

    @Override
    public void start(Stage stage) throws Exception {
        final ApplicationModel model = injector.getInstance(ApplicationModel.class);

        ToolBar toolBar = new ToolBar();
        for (ToolBarContribution contribution : injector.getInstance(ToolBarContributions.class)) {
            contribution.addTo(toolBar);
        }

        FlowPane pane = new FlowPane();
        TextArea textArea = new TextArea();
        textArea.setText(model.message());
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                model.message(s1);
            }
        });
        pane.getChildren().addAll(toolBar, textArea);

        stage.setScene(new Scene(pane));
        stage.show();
    }

}
