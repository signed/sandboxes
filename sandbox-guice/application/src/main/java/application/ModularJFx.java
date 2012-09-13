package application;

import application.input.InputPresenter;
import application.input.InputView;
import application.input.Presenter;
import application.input.SubordinatingView;
import application.recordings.RecordingsModel;
import application.recordings.RecordingsPresenter;
import application.recordings.view.RecordingsView;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import contributions.ApplicationContributionModule;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.ServiceLoader;

public class ModularJFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static List<Module> allGuiceModules(){
        List<Module> allModules = Lists.newArrayList();
        allModules.add(new ApplicationModule());

        ServiceLoader<ApplicationContributionModule> guiceModules = ServiceLoader.load(ApplicationContributionModule.class);
        for (ApplicationContributionModule guiceModule : guiceModules) {
            allModules.add(guiceModule);
        }

        return allModules;
    }

    private final Injector injector = Guice.createInjector(allGuiceModules());

    @Override
    public void start(Stage stage) throws Exception {
        Presenter presenter = putSceneOn(stage);
        linkModelAndView(presenter);
        stage.show();
    }

    private void linkModelAndView(Presenter presenter) {
        injector.getInstance(InputPresenter.class).wireModelAndView();
        presenter.wireModelAndView();
    }

    private Presenter putSceneOn(Stage stage) {
        ToolBar toolBar = createToolBar();

        RecordingsModel model = new RecordingsModel();
        RecordingsView view = new RecordingsView();

        FlowPane pane = new FlowPane();
        SubordinatingView subordinatingView = injector.getInstance(InputView.class);

        pane.getChildren().add(toolBar);
        subordinatingView.addTo(pane);
        view.addTo(pane);
        stage.setScene(new Scene(pane));
        return new RecordingsPresenter(model, view);
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();
        for (ToolBarContribution contribution : injector.getInstance(Key.get(new TypeLiteral<AllContributors<ToolBarContribution>>() { }))) {
            contribution.addTo(toolBar);
        }
        return toolBar;
    }
}
