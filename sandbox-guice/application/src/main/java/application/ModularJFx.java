package application;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import extensionpoints.ViewContribution;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import com.github.signed.microplugin.core.AllContributors;
import com.github.signed.microplugin.core.ApplicationContributionModule;

import java.util.List;
import java.util.ServiceLoader;

public class ModularJFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static List<Module> allGuiceModules(){
        List<Module> allModules = Lists.newArrayList();

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
        stage.show();
    }


    private void putSceneOn(Stage stage) {
        FlowPane pane = new FlowPane();

        ViewContribution filterViewContribution = new ViewContribution() {

            @Override
            public void addTo(Pane pane) {
                TilePane tilePane = new TilePane();

                TextField seriesTextArea = new TextField();
                seriesTextArea.promptTextProperty().setValue("series");

                TextField seasonTextArea = new TextField();
                seasonTextArea.promptTextProperty().setValue("season");

                tilePane.getChildren().addAll(seriesTextArea, seasonTextArea);

                pane.getChildren().add(tilePane);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        for (ViewContribution viewContribution : injector.getInstance(Key.get(new TypeLiteral<AllContributors<ViewContribution>>() { }))) {
            viewContribution.addTo(pane);
        }
        filterViewContribution.addTo(pane);
        stage.setScene(new Scene(pane));
    }
}
