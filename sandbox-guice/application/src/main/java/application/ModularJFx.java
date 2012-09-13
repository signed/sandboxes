package application;

import application.input.InputPresenter;
import application.input.InputView;
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
        putSceneOn(stage);
        linkModelAndView();
        stage.show();
    }

    private void linkModelAndView() {
        injector.getInstance(InputPresenter.class).wireModelAndView();
    }

    private void putSceneOn(Stage stage) {
        ToolBar toolBar = new ToolBar();
        for (ToolBarContribution contribution : injector.getInstance(Key.get(new TypeLiteral<AllContributors<ToolBarContribution>>(){}))) {
            contribution.addTo(toolBar);
        }

        FlowPane pane = new FlowPane();
        InputView inputView = injector.getInstance(InputView.class);

        pane.getChildren().add(toolBar);
        inputView.addTo(pane);
        stage.setScene(new Scene(pane));
    }
}