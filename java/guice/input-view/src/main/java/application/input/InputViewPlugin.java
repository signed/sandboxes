package application.input;

import application.ApplicationModel;
import com.google.inject.Inject;
import extensionpoints.ViewContribution;
import javafx.scene.layout.Pane;

public class InputViewPlugin implements ViewContribution{
    private final InputView view;

    @Inject
    public InputViewPlugin(InputView view, ApplicationModel model) {
        this.view = view;
        new InputPresenter(view, model).wireModelAndView();
    }

    @Override
    public void addTo(Pane pane) {
        view.addTo(pane);
    }
}